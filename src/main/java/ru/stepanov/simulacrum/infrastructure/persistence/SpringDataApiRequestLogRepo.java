package ru.stepanov.simulacrum.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.ApiRequestLogJpaEntity;

public interface SpringDataApiRequestLogRepo extends JpaRepository<ApiRequestLogJpaEntity, Long>, JpaSpecificationExecutor<ApiRequestLogJpaEntity> {
}
