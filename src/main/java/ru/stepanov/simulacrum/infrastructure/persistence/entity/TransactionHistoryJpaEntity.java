package ru.stepanov.simulacrum.infrastructure.persistence.entity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name = "transaction", schema = "simulacrum")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionHistoryJpaEntity {
    @Id
    @Column(name = "transaction_id")
    private String transactionId;

    @Column(name = "account_id")
    private String accountId;

    @Column(name = "status")
    private String status;

    @Column(name = "bank_transaction_code")
    private String bankTransactionCode;

    @Column(name = "booking_date_time")
    private Instant bookingDateTime;

    @Column(name = "value_date_time")
    private Instant valueDateTime;

    @Column(name = "charge_amount")
    private BigDecimal chargeAmount;

    @Column(name = "charge_currency")
    private String chargeCurrency;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "debtor_name")),
            @AttributeOverride(name = "streetName", column = @Column(name = "debtor_street")),
            @AttributeOverride(name = "buildingNumber", column = @Column(name = "debtor_building")),
            @AttributeOverride(name = "postCode", column = @Column(name = "debtor_post_code")),
            @AttributeOverride(name = "townName", column = @Column(name = "debtor_town")),
            @AttributeOverride(name = "country", column = @Column(name = "debtor_country"))
    })
    private DebtorEmbeddable debtor;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "name", column = @Column(name = "creditor_name")),
            @AttributeOverride(name = "streetName", column = @Column(name = "creditor_street")),
            @AttributeOverride(name = "buildingNumber", column = @Column(name = "creditor_building")),
            @AttributeOverride(name = "postCode", column = @Column(name = "creditor_post_code")),
            @AttributeOverride(name = "townName", column = @Column(name = "creditor_town")),
            @AttributeOverride(name = "country", column = @Column(name = "creditor_country"))
    })
    private CreditorEmbeddable creditor;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "accountName", column = @Column(name = "debtor_account_name")),
            @AttributeOverride(name = "accountScheme", column = @Column(name = "debtor_account_scheme")),
            @AttributeOverride(name = "accountIdentification", column = @Column(name = "debtor_account_identification"))
    })
    private CashAccountEmbeddable debtorAccount;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "accountName", column = @Column(name = "creditor_account_name")),
            @AttributeOverride(name = "accountScheme", column = @Column(name = "creditor_account_scheme")),
            @AttributeOverride(name = "accountIdentification", column = @Column(name = "creditor_account_identification"))
    })
    private CashAccountEmbeddable creditorAccount;

    @Embedded
    @AttributeOverride(name = "unstructured", column = @Column(name = "remittance_unstructured"))
    private RemittanceInfoEmbeddable remittance;

    @OneToOne
    @JoinColumn(name = "card_transaction_id")
    private CardTransactionJpaEntity cardTransaction;
}
