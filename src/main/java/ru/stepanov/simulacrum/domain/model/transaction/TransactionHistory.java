package ru.stepanov.simulacrum.domain.model.transaction;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import ru.stepanov.simulacrum.domain.model.shared.Money;

import java.time.Instant;

@Getter
@RequiredArgsConstructor
public class TransactionHistory {
    private final String transactionId;
    private final String accountId;
    @Setter(AccessLevel.PRIVATE)
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
    @Setter(AccessLevel.PRIVATE)
    private String failureCode;
    @Setter(AccessLevel.PRIVATE)
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

    public void accept() {
        setStatus(TransactionStatusCode.AcceptedSettlementInProcess);
    }

    public void reject(String code, String message) {
        setStatus(TransactionStatusCode.Rejected);
        setFailureCode(code);
        setFailureMessage(message);
    }

    public void complete() {
        setStatus(TransactionStatusCode.AcceptedSettlementCompleted);
    }
}
