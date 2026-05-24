package ru.stepanov.simulacrum.infrastructure.web.dto.request;
import ru.stepanov.simulacrum.domain.model.transaction.CreditDebitCode;
import java.math.BigDecimal;
public record GenerateTransactionRequest(Integer mccCode,String merchantName,String merchantId,BigDecimal amount,String currency,CreditDebitCode creditDebitIndicator,String debtorName,String creditorName) {}
