package ru.stepanov.simulacrum.domain.event;

import java.time.Instant;
import java.util.UUID;

public sealed interface DomainEvent permits TransactionCreatedEvent, TransactionStatusChangedEvent {
    UUID messageId();

    Instant occurredAt();
}
