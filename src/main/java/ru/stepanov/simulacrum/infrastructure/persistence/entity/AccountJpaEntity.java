package ru.stepanov.simulacrum.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "account", schema = "simulacrum")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountJpaEntity {
    @Id
    @Column(name = "account_id")
    private String accountId;

    @Column(name = "status_id", nullable = false)
    private Short statusId;

    @Column(name = "account_type_id", nullable = false)
    private Short accountTypeId;

    @Column(name = "status_update_datetime", nullable = false)
    private Instant statusUpdateDateTime;

    @Column(name = "currency_id", nullable = false)
    private Short currencyId;

    @Column(name = "account_description")
    private String accountDescription;
}
