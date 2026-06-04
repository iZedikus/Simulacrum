package ru.stepanov.simulacrum.application.usecase.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.application.port.DomainEventPublisherPort;
import ru.stepanov.simulacrum.domain.event.TransactionCreatedEvent;
import ru.stepanov.simulacrum.domain.model.shared.Money;
import ru.stepanov.simulacrum.domain.model.transaction.*;
import ru.stepanov.simulacrum.domain.repository.TransactionHistoryRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GenerateTransactionUseCase {
    private final TransactionHistoryRepository repo;
    private final DomainEventPublisherPort publisher;

    public TransactionHistory execute(String accountId, Integer mcc, String merchantName, String merchantId, BigDecimal amount, String currency, CreditDebitCode indicator, String debtorName, String creditorName, String remittanceInformation) {
        String txId = "TX-" + UUID.randomUUID().toString().substring(0, 8);
        Instant now = Instant.now();
        var merchantInfo = new MerchantInfo(merchantName, mcc == null ? 0 : mcc, merchantId);
        var debtor = new Debtor(debtorName, List.of(), merchantInfo, null);
        var creditor = new Creditor(creditorName, List.of(), merchantInfo, null);
        var remittance = remittanceInformation != null ? new Unstructured(remittanceInformation) : new Unstructured("Test transaction");
        var tx = new TransactionHistory(txId, accountId, TransactionStatusCode.AcceptedSettlementCompleted, BankTransactionCode.ObPayment,
                now, now, new Money(amount, currency), remittance,
                null, null, creditor, debtor, null, null, null);
        repo.save(tx);
        publisher.publish(new TransactionCreatedEvent(UUID.randomUUID(), now, txId, accountId, "token-" + accountId, amount, currency, indicator, now, now, merchantName, merchantId, mcc, debtorName, creditorName));
        return tx;
    }
}
