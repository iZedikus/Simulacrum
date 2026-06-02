package ru.stepanov.simulacrum.infrastructure.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.TransactionHistoryJpaEntity;


public interface SpringDataTransactionRepo extends JpaRepository<TransactionHistoryJpaEntity, String> {
    Page<TransactionHistoryJpaEntity> findByAccountIdOrderByBookingDateTimeDesc(String accountId, Pageable pageable);

    long countByAccountId(String accountId);

    @Query("""
            select coalesce(sum(t.chargeAmount), 0)
            from TransactionHistoryJpaEntity t
            where t.consentId = :consentId
              and t.status <> 'Rejected'
            """)
    BigDecimal sumNonRejectedDebitAmountByConsentId(@Param("consentId") String consentId);
}
