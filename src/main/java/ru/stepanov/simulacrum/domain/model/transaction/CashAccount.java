package ru.stepanov.simulacrum.domain.model.transaction;

import lombok.Value;

@Value
public class CashAccount {
    String name;
    AccountIdentificationCode schemeName;
    String identification;
}
