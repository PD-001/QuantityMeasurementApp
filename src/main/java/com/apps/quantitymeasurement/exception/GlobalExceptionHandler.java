package com.apps.quantitymeasurement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handles existing QuantityMeasurementException
    @ExceptionHandler(QuantityMeasurementException.class)
    public ResponseEntity<Map<String, Object>> handleQuantityException(
            QuantityMeasurementException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Map.of(
                "error", "Quantity Measurement Error",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
            ));
    }

    // Handles existing DatabaseException
    @ExceptionHandler(DatabaseException.class)
    public ResponseEntity<Map<String, Object>> handleDatabaseException(
            DatabaseException ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of(
                "error", "Database Error",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
            ));
    }

    // Handles bad unit names (e.g. "FEETS" instead of "FEET")
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(
            IllegalArgumentException ex) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Map.of(
                "error", "Invalid Input",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
            ));
    }

    // Catches everything else
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of(
                "error", "Unexpected Error",
                "message", ex.getMessage(),
                "timestamp", LocalDateTime.now().toString()
            ));
    }
}