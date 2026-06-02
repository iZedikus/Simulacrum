package ru.stepanov.simulacrum.infrastructure.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotNull;
import ru.stepanov.simulacrum.domain.model.account.AccountStatus;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangeAccountStatusRequest {
    @NotNull
    private AccountStatus status;
}
