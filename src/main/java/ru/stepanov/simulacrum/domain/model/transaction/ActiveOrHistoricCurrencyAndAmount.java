package ru.stepanov.simulacrum.domain.model.transaction;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class ActiveOrHistoricCurrencyAndAmount {
    BigDecimal amount;
    String currency;
}
