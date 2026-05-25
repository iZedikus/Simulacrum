package ru.stepanov.simulacrum.domain.model.transaction;

import lombok.Value;

@Value
public class PaymentCard {
    String maskedPan;
    CardSchemeNameCode cardSchemeName;
    String expiryDate;
    String additionalCardData;
    CardStatus cardStatus;
}
