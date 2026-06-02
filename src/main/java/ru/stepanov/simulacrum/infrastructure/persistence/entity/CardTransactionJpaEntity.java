package ru.stepanov.simulacrum.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "card_transaction", schema = "simulacrum")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CardTransactionJpaEntity {
    @Id
    @Column(name = "card_transaction_id")
    private String cardTransactionId;

    @Column(name = "authorization_code")
    private String authorizationCode;

    @Column(name = "card_scheme_id", nullable = false)
    private Short cardSchemeId;

    @Column(name = "masked_pan")
    private String maskedPan;

    @Column(name = "expiry_date")
    private String expiryDate;

    @Column(name = "additional_card_data")
    private String additionalCardData;

    @Column(name = "card_status_id", nullable = false)
    private Short cardStatusId;
}
