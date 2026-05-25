package ru.stepanov.simulacrum.infrastructure.persistence.entity;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.embeddable.CashAccountEmbeddable;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.embeddable.CreditorEmbeddable;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.embeddable.DebtorEmbeddable;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.embeddable.RemittanceInfoEmbeddable;

import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "transaction_history", schema = "simulacrum")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryJpaEntity {
    @Id
    private String transactionId;
    private String accountId;
    private String status;
    private String bankTransactionCode;
    private Instant bookingDateTime;
    private Instant valueDateTime;
    private BigDecimal chargeAmount;
    private String chargeCurrency;

    @Embedded
    private DebtorEmbeddable debtor;

    @Embedded
    private CreditorEmbeddable creditor;

    @Embedded
    private CashAccountEmbeddable debtorAccount;

    @Embedded
    private CashAccountEmbeddable creditorAccount;

    @Embedded
    private RemittanceInfoEmbeddable remittance;

    @OneToOne
    private CardTransactionJpaEntity cardTransaction;
}
