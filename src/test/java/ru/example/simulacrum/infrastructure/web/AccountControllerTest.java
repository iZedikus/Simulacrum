package ru.example.simulacrum.infrastructure.web;

import org.junit.jupiter.api.Test;
import ru.stepanov.simulacrum.application.usecase.account.ChangeAccountStatusUseCase;
import ru.stepanov.simulacrum.application.usecase.account.CreateAccountUseCase;
import ru.stepanov.simulacrum.application.usecase.account.GetAccountUseCase;
import ru.stepanov.simulacrum.application.usecase.account.GetAccountsUseCase;
import ru.stepanov.simulacrum.application.usecase.account.exception.AccountNotFoundException;
import ru.stepanov.simulacrum.application.usecase.transaction.GetTransactionsUseCase;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.domain.model.account.AccountStatus;
import ru.stepanov.simulacrum.domain.model.account.AccountType;
import ru.stepanov.simulacrum.domain.model.shared.Money;
import ru.stepanov.simulacrum.domain.model.transaction.BankTransactionCode;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionHistory;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionStatusCode;
import ru.stepanov.simulacrum.domain.repository.AccountRepository;
import ru.stepanov.simulacrum.domain.repository.TransactionHistoryRepository;
import ru.stepanov.simulacrum.infrastructure.web.controller.AccountController;
import ru.stepanov.simulacrum.infrastructure.web.dto.response.TransactionPageResponse;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AccountControllerTest {
    private final InMemoryAccountRepository accountRepository = new InMemoryAccountRepository();
    private final InMemoryTransactionHistoryRepository transactionRepository = new InMemoryTransactionHistoryRepository();
    private final AccountController controller = new AccountController(
            new CreateAccountUseCase(accountRepository),
            new GetAccountsUseCase(accountRepository),
            new GetAccountUseCase(accountRepository),
            new ChangeAccountStatusUseCase(accountRepository),
            new GetTransactionsUseCase(transactionRepository)
    );

    @Test
    void returnsEmptyTransactionHistoryForExistingAccount() {
        accountRepository.save(enabledAccount("account-1"));

        TransactionPageResponse response = controller.transactions("account-1", 0, 20);

        assertEquals(List.of(), response.getContent());
        assertEquals(0, response.getPage());
        assertEquals(20, response.getSize());
        assertEquals(0, response.getTotalElements());
        assertEquals(0, response.getTotalPages());
    }

    @Test
    void returnsSeveralTransactionsAsResponseDtos() {
        accountRepository.save(enabledAccount("account-1"));
        transactionRepository.save(transaction("tx-old", "account-1", Instant.parse("2026-01-01T00:00:00Z"), "10.00"));
        transactionRepository.save(transaction("tx-new", "account-1", Instant.parse("2026-01-02T00:00:00Z"), "15.50"));
        transactionRepository.save(transaction("tx-other", "account-2", Instant.parse("2026-01-03T00:00:00Z"), "99.99"));

        TransactionPageResponse response = controller.transactions("account-1", 0, 10);

        assertEquals(2, response.getContent().size());
        assertEquals("tx-new", response.getContent().get(0).getTransactionId());
        assertEquals("tx-old", response.getContent().get(1).getTransactionId());
        assertEquals("AcceptedSettlementCompleted", response.getContent().get(0).getStatus());
        assertEquals(new BigDecimal("15.50"), response.getContent().get(0).getChargeAmount());
        assertEquals("RUB", response.getContent().get(0).getChargeCurrency());
        assertEquals(2, response.getTotalElements());
        assertEquals(1, response.getTotalPages());
    }

    @Test
    void checksAccountExistsBeforeReturningTransactionHistory() {
        transactionRepository.save(transaction("tx-1", "missing-account", Instant.parse("2026-01-01T00:00:00Z"), "10.00"));

        assertThrows(AccountNotFoundException.class, () -> controller.transactions("missing-account", 0, 20));
        assertEquals(0, transactionRepository.findByAccountIdCalls);
        assertEquals(0, transactionRepository.countByAccountIdCalls);
    }

    private Account enabledAccount(String accountId) {
        return new Account(accountId, AccountStatus.Enabled, Instant.now(), "RUB", AccountType.Personal, "active");
    }

    private TransactionHistory transaction(String transactionId, String accountId, Instant bookingDateTime, String amount) {
        return new TransactionHistory(
                transactionId,
                accountId,
                TransactionStatusCode.AcceptedSettlementCompleted,
                BankTransactionCode.Transfer,
                bookingDateTime,
                bookingDateTime,
                new Money(new BigDecimal(amount), "RUB"),
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
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

    private static final class InMemoryTransactionHistoryRepository implements TransactionHistoryRepository {
        private final List<TransactionHistory> saved = new ArrayList<>();
        private int findByAccountIdCalls;
        private int countByAccountIdCalls;

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
            findByAccountIdCalls++;
            return saved.stream()
                    .filter(tx -> tx.getAccountId().equals(accountId))
                    .sorted(Comparator.comparing(TransactionHistory::getBookingDateTime).reversed())
                    .skip((long) page * size)
                    .limit(size)
                    .toList();
        }

        @Override
        public long countByAccountId(String accountId) {
            countByAccountIdCalls++;
            return saved.stream().filter(tx -> tx.getAccountId().equals(accountId)).count();
        }

        @Override
        public BigDecimal sumNonRejectedDebitAmountByConsentId(String consentId) {
            return BigDecimal.ZERO;
        }
    }
}
