package ru.stepanov.simulacrum.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import ru.stepanov.simulacrum.domain.model.consent.Consent;
import ru.stepanov.simulacrum.domain.model.consent.ConsentStatus;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.ConsentJpaEntity;

import java.time.Instant;
import java.util.UUID;

@Component
public class ConsentPersistenceMapper {
    public ConsentJpaEntity toEntity(Consent consent) {
        Instant revokedAt = consent.getStatus() == ConsentStatus.Revoked ? Instant.now() : null;
        return new ConsentJpaEntity(
                UUID.fromString(consent.getConsentId()),
                consent.getAccountId(),
                consent.getStatus().name(),
                consent.getTotalDebitLimit(),
                consent.getMaxSingleDebit(),
                consent.getCurrency(),
                null,
                consent.getCreditorSystemId(),
                Instant.now(),
                null,
                revokedAt
        );
    }

    public Consent toDomain(ConsentJpaEntity entity) {
        return new Consent(
                entity.getConsentId().toString(),
                entity.getAccountId(),
                entity.getTotalDebitLimit(),
                entity.getMaxSingleDebit(),
                entity.getCurrency(),
                entity.getCreditorSystemId(),
                ConsentStatus.valueOf(entity.getStatus())
        );
    }
}
