package ru.stepanov.simulacrum.domain.model.transaction;

public class CardIndividualTransaction {
    private final String terminalId;
    private final MerchantInfo merchantTerminal;
    private final CreditDebitCode creditDebitIndicator;
    private final ActiveOrHistoricCurrencyAndAmount amountDetails;

    public CardIndividualTransaction(String terminalId, MerchantInfo merchantTerminal, CreditDebitCode creditDebitIndicator, ActiveOrHistoricCurrencyAndAmount amountDetails) {
        this.terminalId = terminalId;
        this.merchantTerminal = merchantTerminal;
        this.creditDebitIndicator = creditDebitIndicator;
        this.amountDetails = amountDetails;
    }

    public String getTerminalId() { return terminalId; }
    public MerchantInfo getMerchantTerminal() { return merchantTerminal; }
    public CreditDebitCode getCreditDebitIndicator() { return creditDebitIndicator; }
    public ActiveOrHistoricCurrencyAndAmount getAmountDetails() { return amountDetails; }
}
