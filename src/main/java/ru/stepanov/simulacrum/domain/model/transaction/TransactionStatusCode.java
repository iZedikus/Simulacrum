package ru.stepanov.simulacrum.domain.model.transaction;

public enum TransactionStatusCode {
    AcceptedSettlementCompleted,
    AcceptedSettlementInProcess,
    AcceptedWithoutPosting,
    Pending,
    Rejected
}
