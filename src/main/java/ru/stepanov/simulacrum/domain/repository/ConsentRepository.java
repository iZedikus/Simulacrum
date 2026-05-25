package ru.stepanov.simulacrum.domain.repository;

import ru.stepanov.simulacrum.domain.model.consent.Consent;

import java.util.Optional;

public interface ConsentRepository {
    Optional<Consent> findById(String id);

    Consent save(Consent consent);
}
