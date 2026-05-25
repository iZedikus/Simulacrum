package ru.stepanov.simulacrum.application.usecase.transaction;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.application.usecase.transaction.exception.TransactionNotFoundException;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionHistory;
import ru.stepanov.simulacrum.domain.repository.TransactionHistoryRepository;

@Service
@RequiredArgsConstructor
public class GetTransactionUseCase {
    private final TransactionHistoryRepository repo;

    public TransactionHistory execute(String transactionId) {
        return repo.findById(transactionId).orElseThrow(() -> new TransactionNotFoundException(transactionId));
    }
}
