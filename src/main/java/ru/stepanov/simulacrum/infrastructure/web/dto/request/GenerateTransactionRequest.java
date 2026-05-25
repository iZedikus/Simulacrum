package ru.stepanov.simulacrum.infrastructure.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stepanov.simulacrum.domain.model.transaction.CreditDebitCode;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateTransactionRequest {
    private Integer mccCode;
    private String merchantName;
    private String merchantId;
    private BigDecimal amount;
    private String currency;
    private CreditDebitCode creditDebitIndicator;
    private String debtorName;
    private String creditorName;
}
