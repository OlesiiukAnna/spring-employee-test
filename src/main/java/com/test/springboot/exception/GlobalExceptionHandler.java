package com.test.springboot.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleRuntimeException(RuntimeException ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("success", false);
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("path", request.getDescription(false));

        if (ex.getMessage().contains("Did not find employee")) {
            errorDetails.put("error", "Employee not found");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDetails);
        }

        errorDetails.put("error", "Internal server error");
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("success", false);
        errorDetails.put("message", "Access denied");
        errorDetails.put("error", "You don't have permission to access this resource");
        errorDetails.put("path", request.getDescription(false));

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorDetails);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgumentException(IllegalArgumentException ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("success", false);
        errorDetails.put("message", ex.getMessage());
        errorDetails.put("error", "Invalid request data");
        errorDetails.put("path", request.getDescription(false));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorDetails);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("timestamp", LocalDateTime.now());
        errorDetails.put("success", false);
        errorDetails.put("message", "An unexpected error occurred");
        errorDetails.put("error", ex.getMessage());
        errorDetails.put("path", request.getDescription(false));

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorDetails);
    }
}
