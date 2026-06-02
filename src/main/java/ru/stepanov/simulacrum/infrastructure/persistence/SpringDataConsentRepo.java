package ru.stepanov.simulacrum.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.ConsentJpaEntity;

import java.util.UUID;

public interface SpringDataConsentRepo extends JpaRepository<ConsentJpaEntity, UUID> {
}
