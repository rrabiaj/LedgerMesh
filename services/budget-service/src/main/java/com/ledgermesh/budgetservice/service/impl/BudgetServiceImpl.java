package com.ledgermesh.budgetservice.service.impl;

import com.ledgermesh.budgetservice.model.Budget;
import com.ledgermesh.budgetservice.repository.BudgetRepository;
import com.ledgermesh.budgetservice.service.BudgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Service
public class BudgetServiceImpl implements BudgetService {

    private static final Logger logger =
            LoggerFactory.getLogger(BudgetServiceImpl.class);

    private final BudgetRepository budgetRepository;

    public BudgetServiceImpl(BudgetRepository budgetRepository) {
        this.budgetRepository = budgetRepository;
    }

    @Override
    @Transactional
    public Budget createBudget(
            UUID userId,
            String category,
            double monthlyLimit) {

        if (userId == null) {
            throw new IllegalArgumentException(
                    "User ID cannot be null"
            );
        }

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException(
                    "Category cannot be null or empty"
            );
        }

        if (monthlyLimit <= 0) {
            throw new IllegalArgumentException(
                    "Monthly limit must be greater than zero"
            );
        }

        String normalizedCategory =
                category.trim().toUpperCase();

        YearMonth currentMonth =
                YearMonth.now();

        boolean budgetExists = budgetRepository
                .findByUserIdAndCategoryAndMonth(
                        userId,
                        normalizedCategory,
                        currentMonth
                )
                .isPresent();

        if (budgetExists) {
            throw new IllegalArgumentException(
                    "Budget already exists for userId=" +
                    userId +
                    ", category=" +
                    normalizedCategory +
                    ", month=" +
                    currentMonth
            );
        }

        Budget budget = new Budget();

        budget.setUserId(userId);
        budget.setCategory(normalizedCategory);
        budget.setMonthlyLimit(
                BigDecimal.valueOf(monthlyLimit)
        );
        budget.setCurrentSpent(BigDecimal.ZERO);
        budget.setMonth(currentMonth);

        Budget savedBudget =
                budgetRepository.save(budget);

        logger.info(
                "Budget created: id={}, userId={}, category={}, limit={}, month={}",
                savedBudget.getId(),
                savedBudget.getUserId(),
                savedBudget.getCategory(),
                savedBudget.getMonthlyLimit(),
                savedBudget.getMonth()
        );

        return savedBudget;
    }

    @Override
    @Transactional
    public void processTransaction(
            UUID userId,
            String category,
            double amount) {

        if (userId == null) {
            throw new IllegalArgumentException(
                    "User ID cannot be null"
            );
        }

        if (category == null || category.isBlank()) {
            throw new IllegalArgumentException(
                    "Category cannot be null or empty"
            );
        }

        if (amount <= 0) {
            throw new IllegalArgumentException(
                    "Transaction amount must be greater than zero"
            );
        }

        String normalizedCategory =
                category.trim().toUpperCase();

        YearMonth currentMonth =
                YearMonth.now();

        budgetRepository
                .findByUserIdAndCategoryAndMonth(
                        userId,
                        normalizedCategory,
                        currentMonth
                )
                .ifPresentOrElse(
                        budget -> updateBudgetSpending(
                                budget,
                                BigDecimal.valueOf(amount)
                        ),
                        () -> logger.warn(
                                "No budget found for userId={}, category={}, month={}",
                                userId,
                                normalizedCategory,
                                currentMonth
                        )
                );
    }

    private void updateBudgetSpending(
            Budget budget,
            BigDecimal transactionAmount) {

        BigDecimal currentSpent =
                budget.getCurrentSpent() == null
                        ? BigDecimal.ZERO
                        : budget.getCurrentSpent();

        BigDecimal updatedSpent =
                currentSpent.add(transactionAmount);

        budget.setCurrentSpent(updatedSpent);

        budgetRepository.save(budget);

        BigDecimal monthlyLimit =
                budget.getMonthlyLimit();

        logger.info(
                "Budget updated: userId={}, category={}, spent={}, limit={}",
                budget.getUserId(),
                budget.getCategory(),
                updatedSpent,
                monthlyLimit
        );

        int comparison =
                updatedSpent.compareTo(monthlyLimit);

        if (comparison > 0) {

            BigDecimal exceededAmount =
                    updatedSpent.subtract(monthlyLimit);

            logger.warn(
                    "BUDGET EXCEEDED: userId={}, category={}, spent={}, limit={}, exceededBy={}",
                    budget.getUserId(),
                    budget.getCategory(),
                    updatedSpent,
                    monthlyLimit,
                    exceededAmount
            );

        } else if (comparison == 0) {

            logger.warn(
                    "BUDGET LIMIT REACHED: userId={}, category={}, spent={}, limit={}",
                    budget.getUserId(),
                    budget.getCategory(),
                    updatedSpent,
                    monthlyLimit
            );

        } else {

            BigDecimal remaining =
                    monthlyLimit.subtract(updatedSpent);

            logger.info(
                    "Budget remaining: userId={}, category={}, remaining={}",
                    budget.getUserId(),
                    budget.getCategory(),
                    remaining
            );
        }
    }
}