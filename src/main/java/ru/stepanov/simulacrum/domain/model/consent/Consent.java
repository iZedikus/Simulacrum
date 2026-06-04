package ru.stepanov.simulacrum.domain.model.consent;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.Instant;

@Getter
@AllArgsConstructor
public class Consent {
    private final String consentId;
    private final String accountId;
    private final BigDecimal totalDebitLimit;
    private final BigDecimal maxSingleDebit;
    private final String currency;
    private final String purposeCode;
    private final String creditorSystemId;
    private ConsentStatus status;
    private Instant grantedAt;
    private Instant expiresAt;
    private Instant revokedAt;

    public Consent(String consentId, String accountId, BigDecimal totalDebitLimit, BigDecimal maxSingleDebit,
                   String currency, String purposeCode, String creditorSystemId, Instant expiresAt) {
        this(consentId, accountId, totalDebitLimit, maxSingleDebit, currency, purposeCode, creditorSystemId,
                ConsentStatus.Active, Instant.now(), expiresAt, null);
    }

    public Consent(String consentId, String accountId, BigDecimal totalDebitLimit, BigDecimal maxSingleDebit,
                   String currency, String creditorSystemId) {
        this(consentId, accountId, totalDebitLimit, maxSingleDebit, currency, null, creditorSystemId,
                ConsentStatus.Active, Instant.now(), null, null);
    }

    public void revoke() {
        this.status = ConsentStatus.Revoked;
        this.revokedAt = Instant.now();
    }
}
