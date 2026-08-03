package com.ledgermesh.budgetservice.exception;

import com.ledgermesh.budgetservice.dto.ApiErrorDTO;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BudgetNotFoundException.class)
    public ResponseEntity<ApiErrorDTO> handleNotFound(BudgetNotFoundException ex, HttpServletRequest request) {
        ApiErrorDTO error = new ApiErrorDTO(ex.getMessage(), Instant.now(), HttpStatus.NOT_FOUND.value(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiErrorDTO> handleValidation(MethodArgumentNotValidException ex, HttpServletRequest request) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));
        ApiErrorDTO error = new ApiErrorDTO(message, Instant.now(), HttpStatus.BAD_REQUEST.value(), request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}