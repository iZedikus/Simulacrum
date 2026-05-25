package ru.stepanov.simulacrum.application.usecase.consent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.domain.model.consent.Consent;
import ru.stepanov.simulacrum.domain.repository.ConsentRepository;

import java.math.BigDecimal;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterConsentUseCase {
    private final ConsentRepository repo;

    public String execute(String accountId, BigDecimal totalDebitLimit, BigDecimal maxSingleDebit, String currency, String creditorSystemId) {
        String consentId = UUID.randomUUID().toString();
        repo.save(new Consent(consentId, accountId, totalDebitLimit, maxSingleDebit, currency, creditorSystemId));
        return consentId;
    }
}
