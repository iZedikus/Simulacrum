package ru.stepanov.simulacrum.application.port;
import ru.stepanov.simulacrum.domain.event.DomainEvent;
public interface DomainEventPublisherPort { void publish(DomainEvent event); }
