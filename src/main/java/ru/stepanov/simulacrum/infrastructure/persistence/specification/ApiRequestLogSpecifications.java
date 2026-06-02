package ru.stepanov.simulacrum.infrastructure.persistence.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.ApiRequestLogJpaEntity;

import java.time.Instant;

public final class ApiRequestLogSpecifications {
    private ApiRequestLogSpecifications() {
    }

    public static Specification<ApiRequestLogJpaEntity> timestampFrom(Instant from) {
        return (root, query, cb) -> from == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("timestamp"), from);
    }

    public static Specification<ApiRequestLogJpaEntity> timestampTo(Instant to) {
        return (root, query, cb) -> to == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("timestamp"), to);
    }

    public static Specification<ApiRequestLogJpaEntity> methodEquals(String method) {
        return (root, query, cb) ->
                method == null || method.isBlank() ? cb.conjunction() : cb.equal(cb.upper(root.get("method")), method.trim().toUpperCase());
    }

    public static Specification<ApiRequestLogJpaEntity> pathContains(String path) {
        return (root, query, cb) ->
                path == null || path.isBlank() ? cb.conjunction() : cb.like(cb.lower(root.get("path")), "%" + path.trim().toLowerCase() + "%");
    }

    public static Specification<ApiRequestLogJpaEntity> excludeSystemEndpoints(boolean exclude) {
        return (root, query, cb) -> exclude ? cb.isFalse(root.get("systemEndpoint")) : cb.conjunction();
    }
}
