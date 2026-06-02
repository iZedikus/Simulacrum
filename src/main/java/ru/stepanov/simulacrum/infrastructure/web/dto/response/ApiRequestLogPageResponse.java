package ru.stepanov.simulacrum.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ApiRequestLogPageResponse {
    private List<ApiRequestLogResponse> content;
    private int page;
    private int size;
    private long totalElements;
    private int totalPages;
}
