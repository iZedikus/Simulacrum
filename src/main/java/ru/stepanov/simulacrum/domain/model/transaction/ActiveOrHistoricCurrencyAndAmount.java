package ru.stepanov.simulacrum.domain.model.transaction;

import java.math.BigDecimal;

public class ActiveOrHistoricCurrencyAndAmount {
    private final BigDecimal amount;
    private final String currency;

    public ActiveOrHistoricCurrencyAndAmount(BigDecimal amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() { return amount; }
    public String getCurrency() { return currency; }
}
