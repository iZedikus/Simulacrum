package ru.stepanov.simulacrum.infrastructure.web.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.stepanov.simulacrum.infrastructure.persistence.SpringDataApiRequestLogRepo;
import ru.stepanov.simulacrum.infrastructure.persistence.entity.ApiRequestLogJpaEntity;

import java.io.IOException;
import java.time.Instant;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class ApiRequestLoggingFilter extends OncePerRequestFilter {
    private static final String REQUEST_ID_HEADER = "X-Request-Id";
    private static final String CORRELATION_ID_HEADER = "X-Correlation-Id";
    private static final String FORWARDED_FOR_HEADER = "X-Forwarded-For";
    private static final String USER_AGENT_HEADER = "User-Agent";

    private final SpringDataApiRequestLogRepo requestLogRepo;

    @Override
    protected boolean shouldNotFilterAsyncDispatch() {
        return true;
    }

    @Override
    protected boolean shouldNotFilterErrorDispatch() {
        return true;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Instant timestamp = Instant.now();
        long startedAtNanos = System.nanoTime();
        Throwable failure = null;

        try {
            filterChain.doFilter(request, response);
        } catch (Throwable ex) {
            failure = ex;
            throw ex;
        } finally {
            persistLogRecord(request, response, timestamp, startedAtNanos, failure);
        }
    }

    private void persistLogRecord(HttpServletRequest request,
                                  HttpServletResponse response,
                                  Instant timestamp,
                                  long startedAtNanos,
                                  Throwable failure) {
        int status = response.getStatus();
        if (failure != null && status < 400) {
            status = HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
        }

        long durationMs = Math.max(0L, (System.nanoTime() - startedAtNanos) / 1_000_000L);
        String requestPath = request.getRequestURI();
        String forwardedFor = request.getHeader(FORWARDED_FOR_HEADER);

        ApiRequestLogJpaEntity entity = new ApiRequestLogJpaEntity(
                null,
                timestamp,
                request.getMethod(),
                requestPath,
                request.getQueryString(),
                status,
                durationMs,
                resolveRemoteAddress(forwardedFor, request.getRemoteAddr()),
                request.getHeader(USER_AGENT_HEADER),
                resolveCorrelationId(request),
                isSystemEndpoint(requestPath)
        );

        requestLogRepo.save(entity);
    }

    private String resolveCorrelationId(HttpServletRequest request) {
        String requestId = request.getHeader(REQUEST_ID_HEADER);
        if (requestId != null && !requestId.isBlank()) {
            return requestId;
        }
        String correlationId = request.getHeader(CORRELATION_ID_HEADER);
        if (correlationId != null && !correlationId.isBlank()) {
            return correlationId;
        }
        return null;
    }

    private String resolveRemoteAddress(String forwardedFor, String remoteAddr) {
        if (forwardedFor == null || forwardedFor.isBlank()) {
            return remoteAddr;
        }
        return forwardedFor.split(",")[0].trim();
    }

    private boolean isSystemEndpoint(String path) {
        return path.startsWith("/actuator")
                || path.startsWith("/v3/api-docs")
                || path.startsWith("/swagger-ui")
                || path.equals("/swagger-ui.html");
    }
}
