package com.ledgermesh.budgetservice.repository;

import com.ledgermesh.budgetservice.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.YearMonth;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository
        extends JpaRepository<Budget, UUID> {

    Optional<Budget> findByUserIdAndCategoryAndMonth(
            UUID userId,
            String category,
            YearMonth month
    );
}