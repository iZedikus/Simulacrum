package ru.stepanov.simulacrum.domain.model.transaction;

import lombok.Value;

@Value
public class IdentificationType {
    String schemeName;
    String identification;
}
