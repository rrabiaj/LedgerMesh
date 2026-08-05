package com.ledgermesh.apigateway.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class FallbackController {

    @RequestMapping("/fallback/accounts")
    public ResponseEntity<Map<String, Object>> accountsFallback() {
        return serviceUnavailable(
                "account-service",
                "Account Service is currently unavailable. Please try again later."
        );
    }

    @RequestMapping("/fallback/auth")
    public ResponseEntity<Map<String, Object>> authFallback() {
        return serviceUnavailable(
                "auth-service",
                "Authentication Service is currently unavailable. Please try again later."
        );
    }

    @RequestMapping("/fallback/transactions")
    public ResponseEntity<Map<String, Object>> transactionsFallback() {
        return serviceUnavailable(
                "transaction-service",
                "Transaction Service is currently unavailable. Please try again later."
        );
    }

    @RequestMapping("/fallback/budgets")
    public ResponseEntity<Map<String, Object>> budgetsFallback() {

        return serviceUnavailable(
                "budget-service",
                "Budget Service is currently unavailable. Please try again later."
        );
    }

    @RequestMapping("/fallback/notifications")
    public ResponseEntity<Map<String, Object>> notificationsFallback() {

        return serviceUnavailable(
                "notification-service",
                "Notification Service is currently unavailable. Please try again later."
        );
    }

    private ResponseEntity<Map<String, Object>> serviceUnavailable(
            String service,
            String message) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "status", 503,
                        "error", "Service Unavailable",
                        "message", message,
                        "service", service
                ));
    }
}