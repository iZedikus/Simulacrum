package ru.stepanov.simulacrum.domain.model.consent;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class Consent {
    private final String consentId;
    private final String accountId;
    private final BigDecimal totalDebitLimit;
    private final BigDecimal maxSingleDebit;
    private final String currency;
    private final String creditorSystemId;
    private ConsentStatus status;

    public Consent(String consentId, String accountId, BigDecimal totalDebitLimit, BigDecimal maxSingleDebit, String currency, String creditorSystemId) {
        this(consentId, accountId, totalDebitLimit, maxSingleDebit, currency, creditorSystemId, ConsentStatus.Active);
    }

    public void revoke() {
        this.status = ConsentStatus.Revoked;
    }
}
