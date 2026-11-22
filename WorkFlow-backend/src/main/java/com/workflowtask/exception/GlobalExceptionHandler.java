package com.workflowtask.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult()
                .getFieldErrors()
                .forEach(error ->
                        errors.put(
                                error.getField(),
                                error.getDefaultMessage()
                        )
                );

        return buildResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation failed",
                errors
        );
    }


    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDeniedException(
            AccessDeniedException ex) {

        return buildResponse(
                HttpStatus.FORBIDDEN.value(),
                "Forbidden",
                Map.of("error", "You do not have permission to perform this action")
        );
    }


    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleAuthenticationException(
            AuthenticationException ex) {

        return buildResponse(
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                Map.of("error", ex.getMessage())
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(
            Exception ex) {

        // Spring's own HTTP exceptions (404/405/415...) already carry the right
        // status; report that instead of turning every one of them into a 500.
        if (ex instanceof ErrorResponse errorResponse) {
            var body = errorResponse.getBody();
            return buildResponse(
                    errorResponse.getStatusCode().value(),
                    body.getTitle() != null ? body.getTitle() : "Request failed",
                    Map.of("error", body.getDetail() != null ? body.getDetail() : "Request failed")
            );
        }

        log.error("Unexpected error", ex);

        return buildResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal server error",
                Map.of("error", "Unexpected error")
        );
    }

    private ResponseEntity<Map<String, Object>> buildResponse(
            int status,
            String message,
            Map<String, String> errors) {

        Map<String, Object> response = new HashMap<>();

        response.put("status", status);
        response.put("message", message);
        response.put("errors", errors);

        return ResponseEntity
                .status(status)
                .body(response);
    }
}
