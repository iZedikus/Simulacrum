package ru.stepanov.simulacrum.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionResponse {
    private String transactionId;
    private String accountId;
    private String status;
    private String bankTransactionCode;
    private Instant bookingDateTime;
    private Instant valueDateTime;
    private BigDecimal chargeAmount;
    private String chargeCurrency;
    private String debtorName;
    private String debtorAccount;
    private String creditorName;
    private String creditorAccount;
    private String merchantName;
    private Integer mccCode;
    private String merchantId;
    private String maskedPan;
    private String cardScheme;
}
