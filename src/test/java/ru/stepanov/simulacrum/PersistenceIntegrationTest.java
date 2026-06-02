package ru.stepanov.simulacrum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.domain.model.account.AccountStatus;
import ru.stepanov.simulacrum.domain.model.account.AccountType;
import ru.stepanov.simulacrum.domain.model.consent.Consent;
import ru.stepanov.simulacrum.domain.model.consent.ConsentStatus;
import ru.stepanov.simulacrum.domain.model.shared.Money;
import ru.stepanov.simulacrum.domain.model.transaction.BankTransactionCode;
import ru.stepanov.simulacrum.domain.model.transaction.Creditor;
import ru.stepanov.simulacrum.domain.model.transaction.Debtor;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionHistory;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionStatusCode;
import ru.stepanov.simulacrum.domain.model.transaction.Unstructured;
import ru.stepanov.simulacrum.domain.repository.AccountRepository;
import ru.stepanov.simulacrum.domain.repository.ConsentRepository;
import ru.stepanov.simulacrum.domain.repository.TransactionHistoryRepository;
import ru.stepanov.simulacrum.infrastructure.persistence.adapter.JpaAccountRepositoryAdapter;
import ru.stepanov.simulacrum.infrastructure.persistence.adapter.JpaConsentRepositoryAdapter;
import ru.stepanov.simulacrum.infrastructure.persistence.adapter.JpaTransactionHistoryRepositoryAdapter;
import ru.stepanov.simulacrum.infrastructure.persistence.mapper.AccountPersistenceMapper;
import ru.stepanov.simulacrum.infrastructure.persistence.mapper.ConsentPersistenceMapper;
import ru.stepanov.simulacrum.infrastructure.persistence.mapper.TransactionHistoryPersistenceMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import({
        JpaAccountRepositoryAdapter.class,
        JpaConsentRepositoryAdapter.class,
        JpaTransactionHistoryRepositoryAdapter.class,
        AccountPersistenceMapper.class,
        ConsentPersistenceMapper.class,
        TransactionHistoryPersistenceMapper.class
})
class PersistenceIntegrationTest {
    @Container
    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:16-alpine")
            .withDatabaseName("simulacrum")
            .withUsername("simulacrum")
            .withPassword("simulacrum");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.flyway.enabled", () -> "true");
        registry.add("spring.flyway.locations", () -> "classpath:db/migration");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
    }

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    ConsentRepository consentRepository;

    @Autowired
    TransactionHistoryRepository transactionHistoryRepository;

    @Test
    void savesAndReadsAccountConsentAndTransactionViaJpaAdapters() {
        Account account = new Account("ACC-1", AccountStatus.Disabled, Instant.parse("2026-06-02T10:15:30Z"), "RUB", AccountType.Business, "Primary account");
        Account savedAccount = accountRepository.save(account);

        assertThat(savedAccount.getAccountId()).isEqualTo("ACC-1");
        assertThat(accountRepository.findById("ACC-1")).get().satisfies(found -> {
            assertThat(found.getStatus()).isEqualTo(AccountStatus.Disabled);
            assertThat(found.getCurrency()).isEqualTo("RUB");
            assertThat(found.getAccountType()).isEqualTo(AccountType.Business);
            assertThat(found.getStatusUpdateDateTime()).isEqualTo(Instant.parse("2026-06-02T10:15:30Z"));
        });
        assertThat(accountRepository.findAll()).extracting(Account::getAccountId).contains("ACC-1");

        String consentId = UUID.randomUUID().toString();
        Consent consent = new Consent(consentId, "ACC-1", new BigDecimal("5000.00"), new BigDecimal("500.00"), "RUB", "CREDITOR-1", ConsentStatus.Active);
        consentRepository.save(consent);

        assertThat(consentRepository.findById(consentId)).get().satisfies(found -> {
            assertThat(found.getAccountId()).isEqualTo("ACC-1");
            assertThat(found.getTotalDebitLimit()).isEqualByComparingTo("5000.00");
            assertThat(found.getMaxSingleDebit()).isEqualByComparingTo("500.00");
            assertThat(found.getStatus()).isEqualTo(ConsentStatus.Active);
        });

        TransactionHistory transaction = new TransactionHistory(
                "TX-1",
                "ACC-1",
                TransactionStatusCode.Pending,
                BankTransactionCode.ObPayment,
                Instant.parse("2026-06-02T11:00:00Z"),
                Instant.parse("2026-06-02T11:01:00Z"),
                new Money(new BigDecimal("42.50"), "RUB"),
                new Unstructured("Invoice 42"),
                null,
                null,
                new Creditor("Shop", List.of(), null, null),
                new Debtor("Customer", List.of(), null, null),
                null,
                null,
                null
        );
        transactionHistoryRepository.save(transaction);

        assertThat(transactionHistoryRepository.findById("TX-1")).get().satisfies(found -> {
            assertThat(found.getAccountId()).isEqualTo("ACC-1");
            assertThat(found.getStatus()).isEqualTo(TransactionStatusCode.Pending);
            assertThat(found.getBankTransactionCode()).isEqualTo(BankTransactionCode.ObPayment);
            assertThat(found.getChargeAmount().getAmount()).isEqualByComparingTo("42.50");
            assertThat(found.getChargeAmount().getCurrency()).isEqualTo("RUB");
            assertThat(found.getRemittanceInformation().getUnstructured()).isEqualTo("Invoice 42");
            assertThat(found.getCreditor().getName()).isEqualTo("Shop");
            assertThat(found.getDebtor().getName()).isEqualTo("Customer");
        });
        assertThat(transactionHistoryRepository.findByAccountId("ACC-1", 0, 10))
                .extracting(TransactionHistory::getTransactionId)
                .containsExactly("TX-1");
    }
}
