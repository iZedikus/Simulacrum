package ru.stepanov.simulacrum.application.usecase.consent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.application.usecase.consent.exception.ConsentNotFoundException;
import ru.stepanov.simulacrum.domain.model.consent.Consent;
import ru.stepanov.simulacrum.domain.repository.ConsentRepository;

@Service
@RequiredArgsConstructor
public class GetConsentUseCase {
    private final ConsentRepository repo;

    public Consent execute(String consentId) {
        return repo.findById(consentId).orElseThrow(() -> new ConsentNotFoundException(consentId));
    }
}
