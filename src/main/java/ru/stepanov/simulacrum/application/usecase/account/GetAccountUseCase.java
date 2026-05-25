package ru.stepanov.simulacrum.application.usecase.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.application.usecase.account.exception.AccountNotFoundException;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.domain.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class GetAccountUseCase {
    private final AccountRepository repo;

    public Account execute(String id) {
        return repo.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
    }
}
