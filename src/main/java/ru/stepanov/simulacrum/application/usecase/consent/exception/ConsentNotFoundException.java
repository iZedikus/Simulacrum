package ru.stepanov.simulacrum.application.usecase.consent.exception;

public class ConsentNotFoundException extends RuntimeException {
    public ConsentNotFoundException(String consentId) {
        super("Consent not found: " + consentId);
    }
}
