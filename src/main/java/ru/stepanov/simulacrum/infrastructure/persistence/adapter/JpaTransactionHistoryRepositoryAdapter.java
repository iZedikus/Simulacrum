package ru.stepanov.simulacrum.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionHistory;
import ru.stepanov.simulacrum.domain.repository.TransactionHistoryRepository;
import ru.stepanov.simulacrum.infrastructure.persistence.SpringDataTransactionRepo;
import ru.stepanov.simulacrum.infrastructure.persistence.mapper.TransactionHistoryPersistenceMapper;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JpaTransactionHistoryRepositoryAdapter implements TransactionHistoryRepository {
    private final SpringDataTransactionRepo springDataRepo;
    private final TransactionHistoryPersistenceMapper mapper;

    @Override
    public Optional<TransactionHistory> findById(String id) {
        return springDataRepo.findById(id).map(mapper::toDomain);
    }

    @Override
    public TransactionHistory save(TransactionHistory tx) {
        return mapper.toDomain(springDataRepo.save(mapper.toEntity(tx)));
    }

    @Override
    public List<TransactionHistory> findByAccountId(String accountId, int page, int size) {
        return springDataRepo.findByAccountIdOrderByBookingDateTimeDesc(accountId, PageRequest.of(page, size))
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
