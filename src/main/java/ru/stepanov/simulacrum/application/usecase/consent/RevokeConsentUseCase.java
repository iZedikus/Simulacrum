package ru.stepanov.simulacrum.application.usecase.consent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.stepanov.simulacrum.application.usecase.consent.exception.ConsentNotFoundException;
import ru.stepanov.simulacrum.domain.repository.ConsentRepository;

@Service
@RequiredArgsConstructor
public class RevokeConsentUseCase {
    private final ConsentRepository repo;

    public void execute(String consentId) {
        var consent = repo.findById(consentId).orElseThrow(() -> new ConsentNotFoundException(consentId));
        consent.revoke();
        repo.save(consent);
    }
}
