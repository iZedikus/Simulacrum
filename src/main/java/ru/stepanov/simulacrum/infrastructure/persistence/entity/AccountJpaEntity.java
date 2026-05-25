package ru.stepanov.simulacrum.infrastructure.persistence.entity;

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
    private String accountId;
    private String status;
    private String accountType;
    private Instant statusUpdateDateTime;
    private String currency;
    private String accountDescription;
}
