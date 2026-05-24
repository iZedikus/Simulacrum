package ru.stepanov.simulacrum.domain.event;
import ru.stepanov.simulacrum.domain.model.transaction.CreditDebitCode;
import java.math.BigDecimal;import java.time.Instant;import java.util.UUID;
public record TransactionCreatedEvent(UUID messageId, Instant occurredAt, String transactionId, String accountId, String paymentToken, BigDecimal amount, String currency, CreditDebitCode creditDebitIndicator, Instant bookingDateTime, Instant valueDateTime, String merchantName, String merchantId, Integer mccCode, String debtorName, String creditorName) implements DomainEvent {}
