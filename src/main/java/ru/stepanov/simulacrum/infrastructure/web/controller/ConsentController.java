package ru.stepanov.simulacrum.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.simulacrum.application.usecase.consent.GetConsentUseCase;
import ru.stepanov.simulacrum.application.usecase.consent.RegisterConsentUseCase;
import ru.stepanov.simulacrum.application.usecase.consent.RevokeConsentUseCase;
import ru.stepanov.simulacrum.domain.model.consent.Consent;
import ru.stepanov.simulacrum.infrastructure.web.dto.request.RegisterConsentRequest;
import ru.stepanov.simulacrum.infrastructure.web.dto.response.ConsentResponse;

@RestController
@RequestMapping("/api/v1/consents")
@RequiredArgsConstructor
public class ConsentController {
    private final RegisterConsentUseCase registerConsentUseCase;
    private final GetConsentUseCase getConsentUseCase;
    private final RevokeConsentUseCase revokeConsentUseCase;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ConsentResponse register(@RequestBody RegisterConsentRequest request) {
        String consentId = registerConsentUseCase.execute(
                request.getAccountId(),
                request.getTotalDebitLimit(),
                request.getMaxSingleDebit(),
                request.getCurrency(),
                request.getCreditorSystemId()
        );
        return toResponse(getConsentUseCase.execute(consentId));
    }

    @GetMapping("/{consentId}")
    public ConsentResponse get(@PathVariable String consentId) {
        return toResponse(getConsentUseCase.execute(consentId));
    }

    @DeleteMapping("/{consentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void revoke(@PathVariable String consentId) {
        revokeConsentUseCase.execute(consentId);
    }

    private ConsentResponse toResponse(Consent consent) {
        return new ConsentResponse(
                consent.getConsentId(),
                consent.getAccountId(),
                consent.getStatus(),
                consent.getTotalDebitLimit(),
                consent.getMaxSingleDebit(),
                consent.getCurrency(),
                consent.getCreditorSystemId()
        );
    }
}
