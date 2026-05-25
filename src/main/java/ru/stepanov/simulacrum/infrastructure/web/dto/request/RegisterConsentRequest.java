package ru.stepanov.simulacrum.infrastructure.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterConsentRequest {
    private String accountId;
    private BigDecimal totalDebitLimit;
    private BigDecimal maxSingleDebit;
    private String currency;
    private String creditorSystemId;
}
