package ru.stepanov.simulacrum.infrastructure.persistence.entity;

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
    private String cardTransactionId;
    private String authorizationCode;
    private String cardScheme;
    private String maskedPan;
    private String expiryDate;
    private String additionalCardData;
    private String cardStatus;
}
