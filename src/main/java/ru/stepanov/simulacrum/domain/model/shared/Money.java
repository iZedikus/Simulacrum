package ru.stepanov.simulacrum.domain.model.shared;

import java.math.BigDecimal;

public record Money(BigDecimal amount, String currency) {
}
