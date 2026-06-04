package ru.stepanov.simulacrum.infrastructure.web.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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
    private String creditDebitIndicator;
    private Instant bookingDateTime;
    private Instant valueDateTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal amount;
    private String currency;
    private PartyResponse debtor;
    private PartyResponse creditor;
    private MerchantResponse merchant;
    private CardTransactionResponse cardTransaction;
    private String remittanceInformation;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class PartyResponse {
        private String name;
        private String accountIdentification;
        private String accountSchemeName;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MerchantResponse {
        private String merchantName;
        private String merchantId;
        private int mccCode;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CardTransactionResponse {
        private String maskedPan;
        private String cardSchemeName;
        private String cardStatus;
    }
}
