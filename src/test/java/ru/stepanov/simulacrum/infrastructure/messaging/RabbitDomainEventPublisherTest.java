package ru.stepanov.simulacrum.infrastructure.messaging;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import ru.stepanov.simulacrum.domain.event.TransactionCreatedEvent;
import ru.stepanov.simulacrum.domain.model.transaction.CreditDebitCode;
import ru.stepanov.simulacrum.infrastructure.config.RabbitMQConfig;
import ru.stepanov.simulacrum.infrastructure.messaging.dto.TransactionCreatedMessage;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RabbitDomainEventPublisherTest {
    @Test
    void shouldPublishContractDtoInsteadOfDomainEventForTransactionCreated() {
        RabbitTemplate rabbitTemplate = mock(RabbitTemplate.class);
        RabbitDomainEventPublisher publisher = new RabbitDomainEventPublisher(rabbitTemplate);
        TransactionCreatedEvent event = new TransactionCreatedEvent(
                UUID.fromString("a1b2c3d4-0000-0000-0000-111111111111"),
                Instant.parse("2026-05-25T10:00:00.000Z"),
                "TX-2026-001",
                "ACC-TEST-001",
                "token-abc-xyz",
                new BigDecimal("350.00"),
                "RUB",
                CreditDebitCode.Debit,
                Instant.parse("2026-05-25T10:00:00.000Z"),
                Instant.parse("2026-05-25T10:00:00.000Z"),
                "Табакoff ООО",
                "MERCH-777",
                5912,
                "Иванов Иван Иванович",
                "Табакoff ООО"
        );
        ArgumentCaptor<Object> payloadCaptor = ArgumentCaptor.forClass(Object.class);

        publisher.publish(event);

        verify(rabbitTemplate).convertAndSend(
                eq(RabbitMQConfig.EXCHANGE),
                eq(RabbitMQConfig.TRANSACTION_CREATED_KEY),
                payloadCaptor.capture()
        );
        assertThat(payloadCaptor.getValue()).isInstanceOf(TransactionCreatedMessage.class);
        assertThat(((TransactionCreatedMessage) payloadCaptor.getValue()).amount()).isEqualTo("350.00");
    }
}
