package ru.stepanov.simulacrum.domain.model.transaction;

public class PaymentCard {
    private final String maskedPan;
    private final CardSchemeNameCode cardSchemeName;
    private final String expiryDate;
    private final String additionalCardData;
    private final CardStatus cardStatus;

    public PaymentCard(String maskedPan, CardSchemeNameCode cardSchemeName, String expiryDate, String additionalCardData, CardStatus cardStatus) {
        this.maskedPan = maskedPan;
        this.cardSchemeName = cardSchemeName;
        this.expiryDate = expiryDate;
        this.additionalCardData = additionalCardData;
        this.cardStatus = cardStatus;
    }

    public String getMaskedPan() { return maskedPan; }
    public CardSchemeNameCode getCardSchemeName() { return cardSchemeName; }
    public String getExpiryDate() { return expiryDate; }
    public String getAdditionalCardData() { return additionalCardData; }
    public CardStatus getCardStatus() { return cardStatus; }
}
