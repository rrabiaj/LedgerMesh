package com.ledgermesh.accountservice.exception;

import java.util.UUID;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException(UUID id) {
        super("Account not found with ID: " + id);
    }
    
}
