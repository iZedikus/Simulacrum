package ru.example.simulacrum.application;

import org.junit.jupiter.api.Test;
import ru.stepanov.simulacrum.application.usecase.transaction.GetTransactionsUseCase;
import ru.stepanov.simulacrum.domain.model.shared.Money;
import ru.stepanov.simulacrum.domain.model.transaction.BankTransactionCode;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionHistory;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionStatusCode;
import ru.stepanov.simulacrum.domain.repository.TransactionHistoryRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetTransactionsUseCaseTest {
    private final InMemoryTransactionHistoryRepository repository = new InMemoryTransactionHistoryRepository();
    private final GetTransactionsUseCase useCase = new GetTransactionsUseCase(repository);

    @Test
    void returnsEmptyHistory() {
        assertEquals(List.of(), useCase.execute("account-1", 0, 20));
        assertEquals(0, useCase.count("account-1"));
    }

    @Test
    void delegatesPaginationAndReturnsMultipleTransactions() {
        repository.save(transaction("tx-1", Instant.parse("2026-01-01T00:00:00Z")));
        repository.save(transaction("tx-2", Instant.parse("2026-01-02T00:00:00Z")));
        repository.save(transaction("tx-3", Instant.parse("2026-01-03T00:00:00Z")));

        List<TransactionHistory> secondPage = useCase.execute("account-1", 1, 2);

        assertEquals(1, secondPage.size());
        assertEquals("tx-1", secondPage.get(0).getTransactionId());
        assertEquals(3, useCase.count("account-1"));
    }

    private TransactionHistory transaction(String transactionId, Instant bookingDateTime) {
        return new TransactionHistory(
                transactionId,
                "account-1",
                TransactionStatusCode.AcceptedSettlementCompleted,
                BankTransactionCode.Transfer,
                bookingDateTime,
                bookingDateTime,
                new Money(BigDecimal.TEN, "RUB"),
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

    private static final class InMemoryTransactionHistoryRepository implements TransactionHistoryRepository {
        private final List<TransactionHistory> saved = new ArrayList<>();

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
            return saved.stream()
                    .filter(tx -> tx.getAccountId().equals(accountId))
                    .sorted(Comparator.comparing(TransactionHistory::getBookingDateTime).reversed())
                    .skip((long) page * size)
                    .limit(size)
                    .toList();
        }

        @Override
        public long countByAccountId(String accountId) {
            return saved.stream().filter(tx -> tx.getAccountId().equals(accountId)).count();
        }

        @Override
        public BigDecimal sumNonRejectedDebitAmountByConsentId(String consentId) {
            return BigDecimal.ZERO;
        }
    }
}
