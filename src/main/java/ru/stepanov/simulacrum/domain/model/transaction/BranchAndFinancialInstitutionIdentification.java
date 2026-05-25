package ru.stepanov.simulacrum.domain.model.transaction;

import lombok.Value;

@Value
public class BranchAndFinancialInstitutionIdentification {
    FinancialInstitutionIdentificationCode schemeName;
    String identification;
}
