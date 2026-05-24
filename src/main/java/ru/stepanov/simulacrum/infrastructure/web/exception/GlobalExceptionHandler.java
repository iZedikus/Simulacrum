package ru.stepanov.simulacrum.infrastructure.web.exception;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import ru.stepanov.simulacrum.application.usecase.account.exception.AccountNotFoundException;
import ru.stepanov.simulacrum.application.usecase.transaction.exception.TransactionNotFoundException;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({AccountNotFoundException.class, TransactionNotFoundException.class})
    public ResponseEntity<Map<String, String>> notFound(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> generic(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", ex.getMessage()));
    }
}
