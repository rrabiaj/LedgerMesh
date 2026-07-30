package com.ledgermesh.budgetservice.service;

import com.ledgermesh.budgetservice.model.Budget;

import java.util.UUID;

public interface BudgetService {

    Budget createBudget(UUID userId, String category, double monthlyLimit);

    void processTransaction(UUID userId, String category, double amount);
    
}
