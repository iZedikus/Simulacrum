package ru.stepanov.simulacrum.domain.model.transaction;

public class BranchAndFinancialInstitutionIdentification {
    private final FinancialInstitutionIdentificationCode schemeName;
    private final String identification;

    public BranchAndFinancialInstitutionIdentification(FinancialInstitutionIdentificationCode schemeName, String identification) {
        this.schemeName = schemeName;
        this.identification = identification;
    }

    public FinancialInstitutionIdentificationCode getSchemeName() { return schemeName; }
    public String getIdentification() { return identification; }
}
