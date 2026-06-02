package ru.stepanov.simulacrum.infrastructure.persistence;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.TransactionHistoryJpaEntity;

import java.util.List;

public interface SpringDataTransactionRepo extends JpaRepository<TransactionHistoryJpaEntity, String> {
    List<TransactionHistoryJpaEntity> findByAccountIdOrderByBookingDateTimeDesc(String accountId, Pageable pageable);

    @Query("""
            select coalesce(sum(t.chargeAmount), 0)
            from TransactionHistoryJpaEntity t
            where t.consentId = :consentId
              and t.status <> 'Rejected'
            """)
    BigDecimal sumNonRejectedDebitAmountByConsentId(@Param("consentId") String consentId);
}
