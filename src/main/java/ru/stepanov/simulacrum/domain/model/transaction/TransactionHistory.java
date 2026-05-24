package ru.stepanov.simulacrum.domain.model.transaction;

import ru.stepanov.simulacrum.domain.model.shared.Money;

import java.time.Instant;

public class TransactionHistory {
    private final String transactionId;
    private final String accountId;
    private TransactionStatusCode status;
    private final BankTransactionCode bankTransactionCode;
    private final Instant bookingDateTime;
    private final Instant valueDateTime;
    private final Money chargeAmount;
    private final CreditDebitCode creditDebitIndicator;
    private final String merchantName;
    private final String merchantId;
    private final Integer mccCode;
    private final String debtorName;
    private final String creditorName;
    private String failureCode;
    private String failureMessage;

    public TransactionHistory(String transactionId, String accountId, TransactionStatusCode status, BankTransactionCode bankTransactionCode, Instant bookingDateTime, Instant valueDateTime, Money chargeAmount, CreditDebitCode creditDebitIndicator, String merchantName, String merchantId, Integer mccCode, String debtorName, String creditorName) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.status = status;
        this.bankTransactionCode = bankTransactionCode;
        this.bookingDateTime = bookingDateTime;
        this.valueDateTime = valueDateTime;
        this.chargeAmount = chargeAmount;
        this.creditDebitIndicator = creditDebitIndicator;
        this.merchantName = merchantName;
        this.merchantId = merchantId;
        this.mccCode = mccCode;
        this.debtorName = debtorName;
        this.creditorName = creditorName;
    }

    public void accept() {
        status = TransactionStatusCode.AcceptedSettlementInProcess;
    }

    public void reject(String code, String message) {
        status = TransactionStatusCode.Rejected;
        failureCode = code;
        failureMessage = message;
    }

    public void complete() {
        status = TransactionStatusCode.AcceptedSettlementCompleted;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public TransactionStatusCode getStatus() {
        return status;
    }

    public BankTransactionCode getBankTransactionCode() {
        return bankTransactionCode;
    }

    public Instant getBookingDateTime() {
        return bookingDateTime;
    }

    public Instant getValueDateTime() {
        return valueDateTime;
    }

    public Money getChargeAmount() {
        return chargeAmount;
    }

    public CreditDebitCode getCreditDebitIndicator() {
        return creditDebitIndicator;
    }

    public String getMerchantName() {
        return merchantName;
    }

    public String getMerchantId() {
        return merchantId;
    }

    public Integer getMccCode() {
        return mccCode;
    }

    public String getDebtorName() {
        return debtorName;
    }

    public String getCreditorName() {
        return creditorName;
    }

    public String getFailureCode() {
        return failureCode;
    }

    public String getFailureMessage() {
        return failureMessage;
    }
}
