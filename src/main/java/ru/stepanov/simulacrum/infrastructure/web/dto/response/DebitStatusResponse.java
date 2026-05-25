package ru.stepanov.simulacrum.infrastructure.web.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DebitStatusResponse {
    private String transactionId;
    private String status;
    private String failureCode;
    private String failureMessage;
}
