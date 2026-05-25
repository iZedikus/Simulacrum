package ru.stepanov.simulacrum.domain.model.transaction;

public class CardTransaction {
    private final String cardTransactionId;
    private final PaymentCard paymentCard;
    private final CardIndividualTransaction cardIndividualTransaction;

    public CardTransaction(String cardTransactionId, PaymentCard paymentCard, CardIndividualTransaction cardIndividualTransaction) {
        this.cardTransactionId = cardTransactionId;
        this.paymentCard = paymentCard;
        this.cardIndividualTransaction = cardIndividualTransaction;
    }

    public String getCardTransactionId() { return cardTransactionId; }
    public PaymentCard getPaymentCard() { return paymentCard; }
    public CardIndividualTransaction getCardIndividualTransaction() { return cardIndividualTransaction; }
}
