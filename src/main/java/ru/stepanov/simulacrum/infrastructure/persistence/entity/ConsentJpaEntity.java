package ru.stepanov.simulacrum.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "consent", schema = "simulacrum")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentJpaEntity {
    @Id
    @Column(name = "consent_id")
    private UUID consentId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "status")
    private String status;

    @Column(name = "total_debit_limit")
    private BigDecimal totalDebitLimit;

    @Column(name = "max_single_debit")
    private BigDecimal maxSingleDebit;

    @Column(name = "currency")
    private String currency;

    @Column(name = "purpose_code")
    private String purposeCode;

    @Column(name = "creditor_system_id")
    private String creditorSystemId;

    @Column(name = "granted_at")
    private Instant grantedAt;

    @Column(name = "expires_at")
    private Instant expiresAt;

    @Column(name = "revoked_at")
    private Instant revokedAt;
}
