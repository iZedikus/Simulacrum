package ru.stepanov.simulacrum.infrastructure.web.controller;

import org.springframework.web.bind.annotation.*;
import ru.stepanov.simulacrum.application.usecase.account.*;
import ru.stepanov.simulacrum.domain.model.account.Account;
import ru.stepanov.simulacrum.infrastructure.web.dto.request.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {
    private final CreateAccountUseCase create;
    private final GetAccountsUseCase getAll;
    private final GetAccountUseCase getOne;
    private final ChangeAccountStatusUseCase change;

    public AccountController(CreateAccountUseCase create, GetAccountsUseCase getAll, GetAccountUseCase getOne, ChangeAccountStatusUseCase change) {
        this.create = create;
        this.getAll = getAll;
        this.getOne = getOne;
        this.change = change;
    }

    @PostMapping
    public Account create(@RequestBody CreateAccountRequest r) {
        return create.execute(r.getAccountId(), r.getCurrency(), r.getAccountType(), r.getAccountDescription());
    }

    @GetMapping
    public List<Account> all() {
        return getAll.execute();
    }

    @GetMapping("/{accountId}")
    public Account one(@PathVariable String accountId) {
        return getOne.execute(accountId);
    }

    @PatchMapping("/{accountId}/status")
    public Account status(@PathVariable String accountId, @RequestBody ChangeAccountStatusRequest r) {
        return change.execute(accountId, r.getStatus());
    }
}
