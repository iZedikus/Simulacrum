package ru.stepanov.simulacrum.application.usecase.transaction.exception;

public class DebitRejectedException extends RuntimeException {
    private final String errorCode;

    public DebitRejectedException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}
