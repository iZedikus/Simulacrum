package ru.stepanov.simulacrum.domain.model.transaction;

import lombok.Value;

@Value
public class CardIndividualTransaction {
    String terminalId;
    MerchantInfo merchantTerminal;
    CreditDebitCode creditDebitIndicator;
    ActiveOrHistoricCurrencyAndAmount amountDetails;
}
