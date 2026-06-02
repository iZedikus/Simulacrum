package ru.stepanov.simulacrum.infrastructure.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import ru.stepanov.simulacrum.domain.event.TransactionCreatedEvent;
import ru.stepanov.simulacrum.domain.model.transaction.CreditDebitCode;
import ru.stepanov.simulacrum.infrastructure.messaging.dto.TransactionCreatedMessage;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class TransactionCreatedMessageTest {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    void shouldSerializeTransactionCreatedMessageExactlyLikeRabbitContractExample() throws Exception {
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

        String json = objectMapper.writeValueAsString(TransactionCreatedMessage.from(event));

        assertThat(json).isEqualTo("""
                {"messageId":"a1b2c3d4-0000-0000-0000-111111111111","occurredAt":"2026-05-25T10:00:00.000Z","transactionId":"TX-2026-001","accountId":"ACC-TEST-001","paymentToken":"token-abc-xyz","amount":"350.00","currency":"RUB","creditDebitIndicator":"Debit","bookingDateTime":"2026-05-25T10:00:00.000Z","valueDateTime":"2026-05-25T10:00:00.000Z","merchantName":"Табакoff ООО","merchantId":"MERCH-777","mccCode":5912,"debtorName":"Иванов Иван Иванович","creditorName":"Табакoff ООО"}""".strip());
    }

    @Test
    void shouldRejectAmountThatCannotBeRepresentedWithExactlyTwoDecimalPlaces() {
        TransactionCreatedEvent event = new TransactionCreatedEvent(
                UUID.fromString("a1b2c3d4-0000-0000-0000-111111111111"),
                Instant.parse("2026-05-25T10:00:00.000Z"),
                "TX-2026-001",
                "ACC-TEST-001",
                "token-abc-xyz",
                new BigDecimal("350.001"),
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

        assertThatThrownBy(() -> TransactionCreatedMessage.from(event))
                .isInstanceOf(ArithmeticException.class);
    }
}
