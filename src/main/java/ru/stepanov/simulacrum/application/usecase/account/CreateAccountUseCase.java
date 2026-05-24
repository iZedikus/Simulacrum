package ru.stepanov.simulacrum.application.usecase.account;
import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.domain.model.account.*;import ru.stepanov.simulacrum.domain.repository.AccountRepository;
@Service public class CreateAccountUseCase { private final AccountRepository repo; public CreateAccountUseCase(AccountRepository repo){this.repo=repo;} public Account execute(String id,String currency,AccountType type,String desc){return repo.save(new Account(id,currency,type,desc));}}
