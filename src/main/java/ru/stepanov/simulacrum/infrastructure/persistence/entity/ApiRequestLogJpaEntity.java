package ru.stepanov.simulacrum.infrastructure.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Table(name = "api_request_log", schema = "simulacrum")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequestLogJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "request_timestamp", nullable = false)
    private Instant timestamp;

    @Column(name = "method", nullable = false, length = 16)
    private String method;

    @Column(name = "path", nullable = false, length = 512)
    private String path;

    @Column(name = "query_string")
    private String queryString;

    @Column(name = "status", nullable = false)
    private Integer status;

    @Column(name = "duration_ms", nullable = false)
    private Long durationMs;

    @Column(name = "remote_address", length = 128)
    private String remoteAddress;

    @Column(name = "user_agent", length = 512)
    private String userAgent;

    @Column(name = "correlation_id", length = 128)
    private String correlationId;

    @Column(name = "system_endpoint", nullable = false)
    private boolean systemEndpoint;
}
