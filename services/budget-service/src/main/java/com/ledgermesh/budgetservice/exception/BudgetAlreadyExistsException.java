package com.ledgermesh.budgetservice.exception;

import java.util.UUID;

public class BudgetAlreadyExistsException extends RuntimeException {

    public BudgetAlreadyExistsException(UUID userId, String category) {
        super("Budget already exists for user: " + userId + " and category: " + category);
    }
    
}
