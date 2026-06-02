package ru.example.simulacrum.application;

import org.junit.jupiter.api.Test;
import ru.stepanov.simulacrum.application.usecase.transaction.SubmitDebitUseCase;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.domain.model.account.AccountStatus;
import ru.stepanov.simulacrum.domain.model.account.AccountType;
import ru.stepanov.simulacrum.domain.model.consent.Consent;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionHistory;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionStatusCode;
import ru.stepanov.simulacrum.domain.repository.AccountRepository;
import ru.stepanov.simulacrum.domain.repository.ConsentRepository;
import ru.stepanov.simulacrum.domain.repository.TransactionHistoryRepository;
import ru.stepanov.simulacrum.infrastructure.web.controller.PaymentController;
import ru.stepanov.simulacrum.infrastructure.web.dto.request.SubmitDebitRequest;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class SubmitDebitUseCaseTest {
    private final InMemoryTransactionHistoryRepository txRepository = new InMemoryTransactionHistoryRepository();
    private final InMemoryConsentRepository consentRepository = new InMemoryConsentRepository();
    private final InMemoryAccountRepository accountRepository = new InMemoryAccountRepository();
    private final SubmitDebitUseCase useCase = new SubmitDebitUseCase(txRepository, consentRepository, accountRepository);

    @Test
    void acceptsValidDebitAsPendingWithoutCompletingIt() {
        accountRepository.save(enabledAccount("account-1", "RUB"));
        consentRepository.save(new Consent("consent-1", "account-1", new BigDecimal("100.00"), new BigDecimal("50.00"), "RUB", "creditor-1"));

        TransactionHistory tx = useCase.execute("consent-1", "account-1", "recipient-token", new BigDecimal("10.00"), "RUB");

        assertEquals(TransactionStatusCode.Pending, tx.getStatus());
        assertEquals("consent-1", tx.getConsentId());
        assertNull(tx.getFailureCode());
        assertNull(tx.getFailureMessage());
        assertEquals(1, txRepository.saved.size());
    }

    @Test
    void rejectsMissingConsentAndStoresFailureDetails() {
        TransactionHistory tx = useCase.execute("missing-consent", "account-1", "recipient-token", new BigDecimal("10.00"), "RUB");

        assertEquals(TransactionStatusCode.Rejected, tx.getStatus());
        assertEquals("CONSENT_NOT_FOUND", tx.getFailureCode());
        assertEquals("Consent does not exist", tx.getFailureMessage());
        assertEquals(1, txRepository.saved.size());
    }

    @Test
    void rejectsDisabledAccount() {
        accountRepository.save(new Account("account-1", AccountStatus.Disabled, Instant.now(), "RUB", AccountType.Personal, "disabled"));
        consentRepository.save(new Consent("consent-1", "account-1", new BigDecimal("100.00"), new BigDecimal("50.00"), "RUB", "creditor-1"));

        TransactionHistory tx = useCase.execute("consent-1", "account-1", "recipient-token", new BigDecimal("10.00"), "RUB");

        assertEquals(TransactionStatusCode.Rejected, tx.getStatus());
        assertEquals("ACCOUNT_NOT_AVAILABLE", tx.getFailureCode());
    }

    @Test
    void rejectsWhenTotalDebitLimitWouldBeExceeded() {
        accountRepository.save(enabledAccount("account-1", "RUB"));
        consentRepository.save(new Consent("consent-1", "account-1", new BigDecimal("15.00"), new BigDecimal("50.00"), "RUB", "creditor-1"));
        txRepository.totalByConsentId.put("consent-1", new BigDecimal("10.00"));

        TransactionHistory tx = useCase.execute("consent-1", "account-1", "recipient-token", new BigDecimal("6.00"), "RUB");

        assertEquals(TransactionStatusCode.Rejected, tx.getStatus());
        assertEquals("TOTAL_DEBIT_LIMIT_EXCEEDED", tx.getFailureCode());
    }

    @Test
    void paymentControllerReturnsActualStoredStatus() {
        PaymentController controller = new PaymentController(useCase, null);
        SubmitDebitRequest request = new SubmitDebitRequest("missing-consent", "account-1", "recipient-token", new BigDecimal("10.00"), "RUB");

        var response = controller.debit(request);

        assertEquals("Rejected", response.getStatus());
    }

    private Account enabledAccount(String accountId, String currency) {
        return new Account(accountId, AccountStatus.Enabled, Instant.now(), currency, AccountType.Personal, "active");
    }

    private static final class InMemoryTransactionHistoryRepository implements TransactionHistoryRepository {
        private final List<TransactionHistory> saved = new ArrayList<>();
        private final Map<String, BigDecimal> totalByConsentId = new HashMap<>();

        @Override
        public Optional<TransactionHistory> findById(String id) {
            return saved.stream().filter(tx -> tx.getTransactionId().equals(id)).findFirst();
        }

        @Override
        public TransactionHistory save(TransactionHistory tx) {
            saved.add(tx);
            return tx;
        }

        @Override
        public List<TransactionHistory> findByAccountId(String accountId, int page, int size) {
            return saved.stream().filter(tx -> tx.getAccountId().equals(accountId)).toList();
        }

        @Override
        public BigDecimal sumNonRejectedDebitAmountByConsentId(String consentId) {
            return totalByConsentId.getOrDefault(consentId, BigDecimal.ZERO);
        }
    }

    private static final class InMemoryConsentRepository implements ConsentRepository {
        private final Map<String, Consent> consents = new HashMap<>();

        @Override
        public Optional<Consent> findById(String id) {
            return Optional.ofNullable(consents.get(id));
        }

        @Override
        public Consent save(Consent consent) {
            consents.put(consent.getConsentId(), consent);
            return consent;
        }
    }

    private static final class InMemoryAccountRepository implements AccountRepository {
        private final Map<String, Account> accounts = new HashMap<>();

        @Override
        public Optional<Account> findById(String id) {
            return Optional.ofNullable(accounts.get(id));
        }

        @Override
        public Account save(Account account) {
            accounts.put(account.getAccountId(), account);
            return account;
        }

        @Override
        public List<Account> findAll() {
            return new ArrayList<>(accounts.values());
        }
    }
}
