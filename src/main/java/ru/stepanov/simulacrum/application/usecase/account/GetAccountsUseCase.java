package ru.stepanov.simulacrum.application.usecase.account;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.domain.repository.AccountRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAccountsUseCase {
    private final AccountRepository repo;

    public List<Account> execute() {
        return repo.findAll();
    }

    public List<Account> execute(int page, int size) {
        return repo.findAll(page, size);
    }

    public long count() {
        return repo.count();
    }
}
