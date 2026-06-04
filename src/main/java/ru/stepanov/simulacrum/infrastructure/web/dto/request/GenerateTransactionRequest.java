package ru.stepanov.simulacrum.infrastructure.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import ru.stepanov.simulacrum.domain.model.transaction.CreditDebitCode;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenerateTransactionRequest {
    @Min(0)
    @Max(9999)
    private Integer mccCode;

    @Size(max = 140)
    private String merchantName;

    @Size(max = 35)
    private String merchantId;

    @NotNull
    @Positive
    @Digits(integer = 18, fraction = 2)
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}")
    private String currency;

    @NotNull
    private CreditDebitCode creditDebitIndicator;

    @Size(max = 140)
    private String debtorName;

    @Size(max = 140)
    private String creditorName;

    private String remittanceInformation;
}
