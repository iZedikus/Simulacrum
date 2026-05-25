package ru.stepanov.simulacrum.domain.model.transaction;

import lombok.Value;

@Value
public class CardTransaction {
    String cardTransactionId;
    PaymentCard paymentCard;
    CardIndividualTransaction cardIndividualTransaction;
}
