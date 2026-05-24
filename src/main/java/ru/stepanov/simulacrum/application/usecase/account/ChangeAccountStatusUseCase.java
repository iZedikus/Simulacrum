package ru.stepanov.simulacrum.application.usecase.account;

import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.application.usecase.account.exception.AccountNotFoundException;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.domain.model.account.AccountStatus;
import ru.stepanov.simulacrum.domain.repository.AccountRepository;

@Service
public class ChangeAccountStatusUseCase {
    private final AccountRepository repo;

    public ChangeAccountStatusUseCase(AccountRepository repo) {
        this.repo = repo;
    }

    public Account execute(String id, AccountStatus status) {
        var a = repo.findById(id).orElseThrow(() -> new AccountNotFoundException(id));
        switch (status) {
            case Enabled -> a.enable();
            case Disabled -> a.disable();
            case Deleted -> a.delete();
        }
        return repo.save(a);
    }
}
