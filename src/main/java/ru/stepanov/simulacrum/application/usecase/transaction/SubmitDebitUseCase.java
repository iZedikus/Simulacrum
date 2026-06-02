package ru.stepanov.simulacrum.application.usecase.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.domain.model.account.AccountStatus;
import ru.stepanov.simulacrum.domain.model.consent.Consent;
import ru.stepanov.simulacrum.domain.model.consent.ConsentStatus;
import ru.stepanov.simulacrum.domain.model.shared.Money;
import ru.stepanov.simulacrum.domain.model.transaction.*;
import ru.stepanov.simulacrum.domain.repository.AccountRepository;
import ru.stepanov.simulacrum.domain.repository.ConsentRepository;
import ru.stepanov.simulacrum.domain.repository.TransactionHistoryRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SubmitDebitUseCase {
    private final TransactionHistoryRepository transactionHistoryRepository;
    private final ConsentRepository consentRepository;
    private final AccountRepository accountRepository;

    public TransactionHistory execute(String consentId, String sourceAccountId, String recipientToken, BigDecimal amount, String currency) {
        TransactionHistory tx = createDebitTransaction(consentId, sourceAccountId, recipientToken, amount, currency);

        if (consentId == null || consentId.isBlank()) {
            return reject(tx, "CONSENT_NOT_FOUND", "Consent does not exist");
        }

        Optional<Consent> consent = consentRepository.findById(consentId);
        if (consent.isEmpty()) {
            return reject(tx, "CONSENT_NOT_FOUND", "Consent does not exist");
        }

        Consent existingConsent = consent.get();
        if (existingConsent.getStatus() != ConsentStatus.Active) {
            return reject(tx, "CONSENT_NOT_ACTIVE", "Consent is not active");
        }

        if (!Objects.equals(existingConsent.getAccountId(), sourceAccountId)) {
            return reject(tx, "ACCOUNT_CONSENT_MISMATCH", "Consent is not assigned to the source account");
        }

        if (sourceAccountId == null || sourceAccountId.isBlank()) {
            return reject(tx, "ACCOUNT_NOT_FOUND", "Source account does not exist");
        }

        Optional<Account> account = accountRepository.findById(sourceAccountId);
        if (account.isEmpty()) {
            return reject(tx, "ACCOUNT_NOT_FOUND", "Source account does not exist");
        }

        Account sourceAccount = account.get();
        if (sourceAccount.getStatus() == AccountStatus.Disabled || sourceAccount.getStatus() == AccountStatus.Deleted) {
            return reject(tx, "ACCOUNT_NOT_AVAILABLE", "Source account is disabled or deleted");
        }

        if (!sameCurrency(currency, existingConsent.getCurrency()) || !sameCurrency(currency, sourceAccount.getCurrency())) {
            return reject(tx, "CURRENCY_MISMATCH", "Request currency does not match consent or source account currency");
        }

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return reject(tx, "INVALID_AMOUNT", "Debit amount must be positive");
        }

        if (existingConsent.getMaxSingleDebit() != null && amount.compareTo(existingConsent.getMaxSingleDebit()) > 0) {
            return reject(tx, "MAX_SINGLE_DEBIT_EXCEEDED", "Debit amount exceeds max single debit limit");
        }

        BigDecimal totalDebitLimit = existingConsent.getTotalDebitLimit();
        if (totalDebitLimit != null) {
            BigDecimal currentTotal = transactionHistoryRepository.sumNonRejectedDebitAmountByConsentId(consentId);
            BigDecimal requestedTotal = (currentTotal == null ? BigDecimal.ZERO : currentTotal).add(amount);
            if (requestedTotal.compareTo(totalDebitLimit) > 0) {
                return reject(tx, "TOTAL_DEBIT_LIMIT_EXCEEDED", "Debit amount exceeds total debit limit");
            }
        }

        return transactionHistoryRepository.save(tx);
    }

    private TransactionHistory reject(TransactionHistory tx, String failureCode, String failureMessage) {
        tx.reject(failureCode, failureMessage);
        return transactionHistoryRepository.save(tx);
    }

    private boolean sameCurrency(String requestedCurrency, String expectedCurrency) {
        return requestedCurrency != null && expectedCurrency != null && requestedCurrency.equalsIgnoreCase(expectedCurrency);
    }

    private TransactionHistory createDebitTransaction(String consentId, String sourceAccountId, String recipientToken, BigDecimal amount, String currency) {
        String txId = "TX-DEBIT-" + UUID.randomUUID().toString().substring(0, 8);
        Instant now = Instant.now();
        var merchantInfo = new MerchantInfo(recipientToken, 0, "RECIPIENT");
        return new TransactionHistory(txId, sourceAccountId, TransactionStatusCode.Pending, BankTransactionCode.ObPayment,
                now, now, new Money(amount, currency), new Unstructured("Debit by consent " + consentId),
                null, null,
                new Creditor(recipientToken, List.of(), merchantInfo, null),
                new Debtor(sourceAccountId, List.of(), merchantInfo, null),
                null, null, null, consentId);
    }
}
