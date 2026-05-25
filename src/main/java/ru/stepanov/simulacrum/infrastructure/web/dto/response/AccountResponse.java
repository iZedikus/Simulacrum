package ru.stepanov.simulacrum.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stepanov.simulacrum.domain.model.account.AccountStatus;
import ru.stepanov.simulacrum.domain.model.account.AccountType;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponse {
    private String accountId;
    private AccountStatus status;
    private AccountType accountType;
    private String currency;
    private String accountDescription;
    private Instant statusUpdateDateTime;
}
