package ru.stepanov.simulacrum.infrastructure.web.dto.request;

import ru.stepanov.simulacrum.domain.model.account.AccountType;

public record CreateAccountRequest(String accountId, String currency, AccountType accountType,
                                   String accountDescription) {
}
