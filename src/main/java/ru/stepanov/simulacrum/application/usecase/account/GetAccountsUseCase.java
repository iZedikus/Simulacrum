package ru.stepanov.simulacrum.application.usecase.account;
import org.springframework.stereotype.Service;import ru.stepanov.simulacrum.domain.model.account.Account;import ru.stepanov.simulacrum.domain.repository.AccountRepository;import java.util.List;
@Service public class GetAccountsUseCase { private final AccountRepository repo; public GetAccountsUseCase(AccountRepository repo){this.repo=repo;} public List<Account> execute(){return repo.findAll();}}
