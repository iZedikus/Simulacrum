package ru.stepanov.simulacrum.application.usecase.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionHistory;
import ru.stepanov.simulacrum.domain.repository.TransactionHistoryRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetTransactionsUseCase {
    private final TransactionHistoryRepository repo;

    public List<TransactionHistory> execute(String accountId, int page, int size) {
        return repo.findByAccountId(accountId, page, size);
    }
}
