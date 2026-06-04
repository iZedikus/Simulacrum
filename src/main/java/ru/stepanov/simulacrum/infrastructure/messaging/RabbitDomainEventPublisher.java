package ru.stepanov.simulacrum.infrastructure.messaging;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import ru.stepanov.simulacrum.application.port.DomainEventPublisherPort;
import ru.stepanov.simulacrum.domain.event.*;
import ru.stepanov.simulacrum.infrastructure.config.RabbitMQConfig;
import ru.stepanov.simulacrum.infrastructure.messaging.dto.TransactionCreatedMessage;

@Component
public class RabbitDomainEventPublisher implements DomainEventPublisherPort {
    private final RabbitTemplate rabbit;

    public RabbitDomainEventPublisher(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    public void publish(DomainEvent event) {
        if (event instanceof TransactionCreatedEvent e) {
            rabbit.convertAndSend(
                    RabbitMQConfig.EXCHANGE,
                    RabbitMQConfig.TRANSACTION_CREATED_KEY,
                    TransactionCreatedMessage.from(e)
            );
        }
    }
}
