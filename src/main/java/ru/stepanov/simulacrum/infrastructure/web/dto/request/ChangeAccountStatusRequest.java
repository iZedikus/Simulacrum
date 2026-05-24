package ru.stepanov.simulacrum.infrastructure.web.dto.request;
import ru.stepanov.simulacrum.domain.model.account.AccountStatus;
public record ChangeAccountStatusRequest(AccountStatus status) {}
