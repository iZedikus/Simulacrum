package ru.stepanov.simulacrum.infrastructure.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stepanov.simulacrum.domain.model.account.AccountStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAccountStatusRequest {
    private AccountStatus status;
}
