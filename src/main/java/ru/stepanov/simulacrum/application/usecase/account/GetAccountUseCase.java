package ru.stepanov.simulacrum.application.usecase.account;
import org.springframework.stereotype.Service;import ru.stepanov.simulacrum.application.usecase.account.exception.AccountNotFoundException;import ru.stepanov.simulacrum.domain.model.account.Account;import ru.stepanov.simulacrum.domain.repository.AccountRepository;
@Service public class GetAccountUseCase { private final AccountRepository repo; public GetAccountUseCase(AccountRepository repo){this.repo=repo;} public Account execute(String id){return repo.findById(id).orElseThrow(() -> new AccountNotFoundException(id));}}
