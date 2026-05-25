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
    private final Unstructured remittanceInformation;
    private final BranchAndFinancialInstitutionIdentification creditorAgent;
    private final BranchAndFinancialInstitutionIdentification debtorAgent;
    private final Creditor creditor;
    private final Debtor debtor;
    private final CashAccount creditorAccount;
    private final CashAccount debtorAccount;
    private final CardTransaction cardTransaction;
    private String failureCode;
    private String failureMessage;

    public TransactionHistory(String transactionId, String accountId, TransactionStatusCode status, BankTransactionCode bankTransactionCode,
                              Instant bookingDateTime, Instant valueDateTime, Money chargeAmount, Unstructured remittanceInformation,
                              BranchAndFinancialInstitutionIdentification creditorAgent, BranchAndFinancialInstitutionIdentification debtorAgent,
                              Creditor creditor, Debtor debtor, CashAccount creditorAccount, CashAccount debtorAccount,
                              CardTransaction cardTransaction) {
        this.transactionId = transactionId;
        this.accountId = accountId;
        this.status = status;
        this.bankTransactionCode = bankTransactionCode;
        this.bookingDateTime = bookingDateTime;
        this.valueDateTime = valueDateTime;
        this.chargeAmount = chargeAmount;
        this.remittanceInformation = remittanceInformation;
        this.creditorAgent = creditorAgent;
        this.debtorAgent = debtorAgent;
        this.creditor = creditor;
        this.debtor = debtor;
        this.creditorAccount = creditorAccount;
        this.debtorAccount = debtorAccount;
        this.cardTransaction = cardTransaction;
    }

    public void accept() { status = TransactionStatusCode.AcceptedSettlementInProcess; }
    public void reject(String code, String message) { status = TransactionStatusCode.Rejected; failureCode = code; failureMessage = message; }
    public void complete() { status = TransactionStatusCode.AcceptedSettlementCompleted; }

    public String getTransactionId() { return transactionId; }
    public String getAccountId() { return accountId; }
    public TransactionStatusCode getStatus() { return status; }
    public BankTransactionCode getBankTransactionCode() { return bankTransactionCode; }
    public Instant getBookingDateTime() { return bookingDateTime; }
    public Instant getValueDateTime() { return valueDateTime; }
    public Money getChargeAmount() { return chargeAmount; }
    public Unstructured getRemittanceInformation() { return remittanceInformation; }
    public BranchAndFinancialInstitutionIdentification getCreditorAgent() { return creditorAgent; }
    public BranchAndFinancialInstitutionIdentification getDebtorAgent() { return debtorAgent; }
    public Creditor getCreditor() { return creditor; }
    public Debtor getDebtor() { return debtor; }
    public CashAccount getCreditorAccount() { return creditorAccount; }
    public CashAccount getDebtorAccount() { return debtorAccount; }
    public CardTransaction getCardTransaction() { return cardTransaction; }
    public String getFailureCode() { return failureCode; }
    public String getFailureMessage() { return failureMessage; }
}
