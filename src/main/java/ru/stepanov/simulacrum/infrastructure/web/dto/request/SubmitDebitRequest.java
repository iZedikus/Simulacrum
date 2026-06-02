package ru.stepanov.simulacrum.infrastructure.web.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubmitDebitRequest {
    @NotBlank
    @Size(max = 35)
    private String consentId;

    @NotBlank
    @Size(max = 35)
    private String sourceAccountId;

    @NotBlank
    @Size(max = 140)
    private String recipientPaymentToken;

    @NotNull
    @Positive
    @Digits(integer = 18, fraction = 2)
    private BigDecimal amount;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}")
    private String currency;
}
