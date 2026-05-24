package ru.stepanov.simulacrum.infrastructure.web.controller;

import org.springframework.web.bind.annotation.*;
import ru.stepanov.simulacrum.application.usecase.transaction.GenerateTransactionUseCase;
import ru.stepanov.simulacrum.domain.model.transaction.TransactionHistory;
import ru.stepanov.simulacrum.infrastructure.web.dto.request.GenerateTransactionRequest;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {
    private final GenerateTransactionUseCase generate;

    public TransactionController(GenerateTransactionUseCase generate) {
        this.generate = generate;
    }

    @PostMapping("/admin/accounts/{accountId}/transactions/generate")
    public TransactionHistory generate(@PathVariable String accountId, @RequestBody GenerateTransactionRequest r) {
        return generate.execute(accountId, r.mccCode(), r.merchantName(), r.merchantId(), r.amount(), r.currency(), r.creditDebitIndicator(), r.debtorName(), r.creditorName());
    }
}
