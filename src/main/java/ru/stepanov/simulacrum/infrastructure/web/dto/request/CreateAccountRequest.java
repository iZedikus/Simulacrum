package ru.stepanov.simulacrum.infrastructure.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stepanov.simulacrum.domain.model.account.AccountType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateAccountRequest {
    private String accountId;
    private String currency;
    private AccountType accountType;
    private String accountDescription;
}
