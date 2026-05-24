package ru.stepanov.simulacrum.infrastructure.web.dto.request;

import java.math.BigDecimal;

public record SubmitDebitRequest(String consentId, String sourceAccountId, String recipientPaymentToken,
                                 BigDecimal amount, String currency) {
}
