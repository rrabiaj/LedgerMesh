package com.ledgermesh.budgetservice.repository;

import com.ledgermesh.budgetservice.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BudgetRepository extends JpaRepository<Budget, UUID> {

    Optional<Budget> findByUserIdAndCategory(UUID userId, String category);

    List<Budget> findByUserId(UUID userId);
}