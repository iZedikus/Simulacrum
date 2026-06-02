package ru.stepanov.simulacrum.domain.repository;

import ru.stepanov.simulacrum.domain.model.transaction.TransactionHistory;

import java.math.BigDecimal;
import java.util.*;

public interface TransactionHistoryRepository {
    Optional<TransactionHistory> findById(String id);

    TransactionHistory save(TransactionHistory tx);

    List<TransactionHistory> findByAccountId(String accountId, int page, int size);

    long countByAccountId(String accountId);

    BigDecimal sumNonRejectedDebitAmountByConsentId(String consentId);
}
