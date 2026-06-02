package ru.stepanov.simulacrum.infrastructure.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.stepanov.simulacrum.infrastructure.persistence.SpringDataApiRequestLogRepo;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.ApiRequestLogJpaEntity;
import ru.stepanov.simulacrum.infrastructure.persistence.specification.ApiRequestLogSpecifications;
import ru.stepanov.simulacrum.infrastructure.web.dto.response.ApiRequestLogPageResponse;
import ru.stepanov.simulacrum.infrastructure.web.dto.response.ApiRequestLogResponse;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/request-log")
@RequiredArgsConstructor
public class AdminRequestLogController {
    private final SpringDataApiRequestLogRepo requestLogRepo;

    @GetMapping
    public ApiRequestLogPageResponse getRequestLog(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant from,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Instant to,
            @RequestParam(required = false) String method,
            @RequestParam(required = false) String path,
            @RequestParam(defaultValue = "false") boolean includeSystem
    ) {
        int normalizedPage = Math.max(page, 0);
        int normalizedSize = Math.max(size, 1);

        Specification<ApiRequestLogJpaEntity> specification =
                ApiRequestLogSpecifications.timestampFrom(from)
                        .and(ApiRequestLogSpecifications.timestampTo(to))
                        .and(ApiRequestLogSpecifications.methodEquals(method))
                        .and(ApiRequestLogSpecifications.pathContains(path))
                        .and(ApiRequestLogSpecifications.excludeSystemEndpoints(!includeSystem));

        Pageable pageable = PageRequest.of(normalizedPage, normalizedSize, Sort.by(Sort.Direction.DESC, "timestamp"));
        Page<ApiRequestLogJpaEntity> resultPage = requestLogRepo.findAll(specification, pageable);
        List<ApiRequestLogResponse> content = resultPage.getContent().stream().map(this::toResponse).toList();

        return new ApiRequestLogPageResponse(
                content,
                resultPage.getNumber(),
                resultPage.getSize(),
                resultPage.getTotalElements(),
                resultPage.getTotalPages()
        );
    }

    private ApiRequestLogResponse toResponse(ApiRequestLogJpaEntity entity) {
        return new ApiRequestLogResponse(
                entity.getId(),
                entity.getTimestamp(),
                entity.getMethod(),
                entity.getPath(),
                entity.getQueryString(),
                entity.getStatus(),
                entity.getDurationMs(),
                entity.getRemoteAddress(),
                entity.getUserAgent(),
                entity.getCorrelationId(),
                entity.isSystemEndpoint()
        );
    }
}
