package ru.stepanov.simulacrum.infrastructure.persistence.adapter;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.stepanov.simulacrum.domain.model.consent.Consent;
import ru.stepanov.simulacrum.domain.repository.ConsentRepository;
import ru.stepanov.simulacrum.infrastructure.persistence.SpringDataConsentRepo;
import ru.stepanov.simulacrum.infrastructure.persistence.mapper.ConsentPersistenceMapper;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaConsentRepositoryAdapter implements ConsentRepository {
    private final SpringDataConsentRepo springDataRepo;
    private final ConsentPersistenceMapper mapper;

    @Override
    public Optional<Consent> findById(String id) {
        try {
            return springDataRepo.findById(UUID.fromString(id)).map(mapper::toDomain);
        } catch (IllegalArgumentException ex) {
            return Optional.empty();
        }
    }

    @Override
    public Consent save(Consent consent) {
        return mapper.toDomain(springDataRepo.save(mapper.toEntity(consent)));
    }
}
