package ru.stepanov.simulacrum.domain.model.transaction;

public class IdentificationType {
    private final String schemeName;
    private final String identification;

    public IdentificationType(String schemeName, String identification) {
        this.schemeName = schemeName;
        this.identification = identification;
    }

    public String getSchemeName() { return schemeName; }
    public String getIdentification() { return identification; }
}
