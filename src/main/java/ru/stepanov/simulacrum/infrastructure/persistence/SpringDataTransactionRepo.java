package ru.stepanov.simulacrum.infrastructure.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.TransactionHistoryJpaEntity;

import java.util.List;

public interface SpringDataTransactionRepo extends JpaRepository<TransactionHistoryJpaEntity, String> {
    List<TransactionHistoryJpaEntity> findByAccountIdOrderByBookingDateTimeDesc(String accountId, Pageable pageable);
}
