package ru.stepanov.simulacrum.domain.model.shared;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class Money {
    BigDecimal amount;
    String currency;
}
