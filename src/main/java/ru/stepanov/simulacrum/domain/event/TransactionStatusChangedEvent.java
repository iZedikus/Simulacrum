package ru.stepanov.simulacrum.domain.event;

import ru.stepanov.simulacrum.domain.model.transaction.TransactionStatusCode;

import java.time.Instant;
import java.util.UUID;

public record TransactionStatusChangedEvent(UUID messageId, Instant occurredAt, String transactionId,
                                            TransactionStatusCode status) implements DomainEvent {
}
