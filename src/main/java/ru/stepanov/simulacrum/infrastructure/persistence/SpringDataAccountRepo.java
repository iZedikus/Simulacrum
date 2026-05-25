package ru.stepanov.simulacrum.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.AccountJpaEntity;

public interface SpringDataAccountRepo extends JpaRepository<AccountJpaEntity, String> {
}
