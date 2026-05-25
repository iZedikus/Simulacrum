package ru.stepanov.simulacrum.infrastructure.persistence.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "consent", schema = "simulacrum")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentJpaEntity {
    @Id
    private String consentId;
    private String accountId;
    private String status;
    private BigDecimal totalDebitLimit;
    private BigDecimal maxSingleDebit;
    private String currency;
    private String purposeCode;
    private String creditorSystemId;
    private Instant grantedAt;
    private Instant expiresAt;
    private Instant revokedAt;
}
