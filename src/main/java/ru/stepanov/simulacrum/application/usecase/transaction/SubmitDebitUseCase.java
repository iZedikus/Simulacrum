package ru.stepanov.simulacrum.application.usecase.transaction;

import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.domain.model.shared.Money;
import ru.stepanov.simulacrum.domain.model.transaction.*;
import ru.stepanov.simulacrum.domain.repository.TransactionHistoryRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class SubmitDebitUseCase {
    private final TransactionHistoryRepository repo;

    public SubmitDebitUseCase(TransactionHistoryRepository repo) {
        this.repo = repo;
    }

    public TransactionHistory execute(String consentId, String sourceAccountId, String recipientToken, BigDecimal amount, String currency) {
        String txId = "TX-DEBIT-" + UUID.randomUUID().toString().substring(0, 8);
        var merchantInfo = new MerchantInfo(recipientToken, 0, "RECIPIENT");
        var tx = new TransactionHistory(txId, sourceAccountId, TransactionStatusCode.Pending, BankTransactionCode.ObPayment,
                Instant.now(), Instant.now(), new Money(amount, currency), new Unstructured("Debit by consent " + consentId),
                null, null,
                new Creditor(recipientToken, List.of(), merchantInfo, null),
                new Debtor(sourceAccountId, List.of(), merchantInfo, null),
                null, null, null);
        repo.save(tx);
        tx.complete();
        return repo.save(tx);
    }
}
