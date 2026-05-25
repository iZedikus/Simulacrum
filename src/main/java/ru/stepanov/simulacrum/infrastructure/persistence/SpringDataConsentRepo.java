package ru.stepanov.simulacrum.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.ConsentJpaEntity;

public interface SpringDataConsentRepo extends JpaRepository<ConsentJpaEntity, String> {
}
