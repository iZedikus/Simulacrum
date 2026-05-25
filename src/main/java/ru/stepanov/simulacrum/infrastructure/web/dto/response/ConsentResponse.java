package ru.stepanov.simulacrum.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stepanov.simulacrum.domain.model.consent.ConsentStatus;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentResponse {
    private String consentId;
    private String accountId;
    private ConsentStatus status;
    private BigDecimal totalDebitLimit;
    private BigDecimal maxSingleDebit;
    private String currency;
    private String creditorSystemId;
}
