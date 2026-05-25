package ru.stepanov.simulacrum.application.usecase.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.domain.model.account.AccountType;
import ru.stepanov.simulacrum.domain.repository.AccountRepository;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase {
    private final AccountRepository repo;

    public Account execute(String id, String currency, AccountType type, String desc) {
        return repo.save(new Account(id, currency, type, desc));
    }
}
