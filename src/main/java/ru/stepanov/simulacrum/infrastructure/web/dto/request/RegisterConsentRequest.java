package ru.stepanov.simulacrum.infrastructure.web.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
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
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterConsentRequest {
    @NotBlank
    @Size(max = 35)
    private String accountId;

    @NotNull
    @Positive
    @Digits(integer = 18, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalDebitLimit;

    @Positive
    @Digits(integer = 18, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal maxSingleDebit;

    @NotBlank
    @Pattern(regexp = "[A-Z]{3}")
    private String currency;

    @Size(max = 10)
    private String purposeCode;

    @NotBlank
    @Size(max = 35)
    private String creditorSystemId;

    private Instant expiresAt;
}
