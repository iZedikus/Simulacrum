package ru.stepanov.simulacrum.domain.model.transaction;

public class CashAccount {
    private final String name;
    private final AccountIdentificationCode schemeName;
    private final String identification;

    public CashAccount(String name, AccountIdentificationCode schemeName, String identification) {
        this.name = name;
        this.schemeName = schemeName;
        this.identification = identification;
    }

    public String getName() { return name; }
    public AccountIdentificationCode getSchemeName() { return schemeName; }
    public String getIdentification() { return identification; }
}
