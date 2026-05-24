package ru.stepanov.simulacrum.infrastructure.web.dto.response;
public record DebitStatusResponse(String transactionId, String status, String failureCode, String failureMessage) {}
