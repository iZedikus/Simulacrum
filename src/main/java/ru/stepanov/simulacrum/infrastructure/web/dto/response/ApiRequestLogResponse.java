package ru.stepanov.simulacrum.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequestLogResponse {
    private Long id;
    private Instant timestamp;
    private String method;
    private String path;
    private String queryString;
    private Integer status;
    private Long durationMs;
    private String remoteAddress;
    private String userAgent;
    private String correlationId;
    private boolean systemEndpoint;
}
