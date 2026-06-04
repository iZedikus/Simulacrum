package ru.stepanov.simulacrum.infrastructure.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.stepanov.simulacrum.domain.model.consent.ConsentStatus;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsentResponse {
    private String consentId;
    private String accountId;
    private ConsentStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal totalDebitLimit;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal maxSingleDebit;
    private String currency;
    private String purposeCode;
    private String creditorSystemId;
    private Instant grantedAt;
    private Instant expiresAt;
    private Instant revokedAt;
}
