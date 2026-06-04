package ru.stepanov.simulacrum.infrastructure.persistence.mapper;

import org.springframework.stereotype.Component;
import ru.stepanov.simulacrum.domain.model.consent.Consent;
import ru.stepanov.simulacrum.domain.model.consent.ConsentStatus;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.ConsentJpaEntity;

import java.util.UUID;

@Component
public class ConsentPersistenceMapper {
    public ConsentJpaEntity toEntity(Consent consent) {
        return new ConsentJpaEntity(
                UUID.fromString(consent.getConsentId()),
                consent.getAccountId(),
                consent.getStatus().name(),
                consent.getTotalDebitLimit(),
                consent.getMaxSingleDebit(),
                consent.getCurrency(),
                consent.getPurposeCode(),
                consent.getCreditorSystemId(),
                consent.getGrantedAt(),
                consent.getExpiresAt(),
                consent.getRevokedAt()
        );
    }

    public Consent toDomain(ConsentJpaEntity entity) {
        return new Consent(
                entity.getConsentId().toString(),
                entity.getAccountId(),
                entity.getTotalDebitLimit(),
                entity.getMaxSingleDebit(),
                entity.getCurrency(),
                entity.getPurposeCode(),
                entity.getCreditorSystemId(),
                ConsentStatus.valueOf(entity.getStatus()),
                entity.getGrantedAt(),
                entity.getExpiresAt(),
                entity.getRevokedAt()
        );
    }
}
