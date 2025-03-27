package org.sieniawski.projekt3.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<String> handleValidationException(WebExchangeBindException ex) {
        log.error("Validation error: {}", ex.getMessage(), ex);
        String errorMessage = ex.getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .reduce("", (a, b) -> a + b + "; ");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorMessage.trim());
    }



    @ExceptionHandler(IllegalStateException.class)
    public ResponseEntity<String> handleValidationException(IllegalStateException ex) {
        log.error("^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+ex.getMessage(), ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}