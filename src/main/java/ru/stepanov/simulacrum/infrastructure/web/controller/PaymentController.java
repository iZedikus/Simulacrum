package ru.stepanov.simulacrum.infrastructure.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.simulacrum.application.usecase.transaction.*;
import ru.stepanov.simulacrum.infrastructure.web.dto.request.SubmitDebitRequest;
import ru.stepanov.simulacrum.infrastructure.web.dto.response.*;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
    private final SubmitDebitUseCase submit;
    private final GetDebitStatusUseCase status;

    public PaymentController(SubmitDebitUseCase submit, GetDebitStatusUseCase status) {
        this.submit = submit;
        this.status = status;
    }

    @PostMapping("/debit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public SubmitDebitResponse debit(@RequestBody SubmitDebitRequest r) {
        var tx = submit.execute(r.getConsentId(), r.getSourceAccountId(), r.getRecipientPaymentToken(), r.getAmount(), r.getCurrency());
        return new SubmitDebitResponse(tx.getTransactionId(), "Pending");
    }

    @GetMapping("/{transactionId}/status")
    public DebitStatusResponse getStatus(@PathVariable String transactionId) {
        var tx = status.execute(transactionId);
        return new DebitStatusResponse(tx.getTransactionId(), tx.getStatus().name(), tx.getFailureCode(), tx.getFailureMessage());
    }
}
