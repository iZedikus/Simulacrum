package ru.stepanov.simulacrum.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.TransactionHistoryJpaEntity;

public interface SpringDataTransactionRepo extends JpaRepository<TransactionHistoryJpaEntity, String> {
}
