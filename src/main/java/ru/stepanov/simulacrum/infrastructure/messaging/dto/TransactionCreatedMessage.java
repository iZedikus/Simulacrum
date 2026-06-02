package ru.stepanov.simulacrum.infrastructure.messaging.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import ru.stepanov.simulacrum.domain.event.TransactionCreatedEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public record TransactionCreatedMessage(
        @JsonProperty("messageId") String messageId,
        @JsonProperty("occurredAt") String occurredAt,
        @JsonProperty("transactionId") String transactionId,
        @JsonProperty("accountId") String accountId,
        @JsonProperty("paymentToken") String paymentToken,
        @JsonProperty("amount") String amount,
        @JsonProperty("currency") String currency,
        @JsonProperty("creditDebitIndicator") String creditDebitIndicator,
        @JsonProperty("bookingDateTime") String bookingDateTime,
        @JsonProperty("valueDateTime") String valueDateTime,
        @JsonProperty("merchantName") String merchantName,
        @JsonProperty("merchantId") String merchantId,
        @JsonProperty("mccCode") Integer mccCode,
        @JsonProperty("debtorName") String debtorName,
        @JsonProperty("creditorName") String creditorName
) {
    private static final DateTimeFormatter CONTRACT_INSTANT_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
                    .withZone(ZoneOffset.UTC);

    public static TransactionCreatedMessage from(TransactionCreatedEvent event) {
        return new TransactionCreatedMessage(
                event.messageId().toString(),
                formatInstant(event.occurredAt()),
                event.transactionId(),
                event.accountId(),
                event.paymentToken(),
                formatAmount(event.amount()),
                event.currency(),
                event.creditDebitIndicator().name(),
                formatInstant(event.bookingDateTime()),
                formatInstant(event.valueDateTime()),
                event.merchantName(),
                event.merchantId(),
                event.mccCode(),
                event.debtorName(),
                event.creditorName()
        );
    }

    private static String formatAmount(BigDecimal amount) {
        return amount.setScale(2, RoundingMode.UNNECESSARY).toPlainString();
    }

    private static String formatInstant(Instant instant) {
        return instant == null ? null : CONTRACT_INSTANT_FORMATTER.format(instant);
    }
}
