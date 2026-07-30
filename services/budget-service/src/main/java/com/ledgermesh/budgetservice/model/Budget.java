package com.ledgermesh.budgetservice.model;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.util.UUID;

@Entity
@Table(name = "budgets")
public class Budget {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private UUID userId;

    @Column(nullable = false)
    private BigDecimal monthlyLimit;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal currentSpent = BigDecimal.ZERO;

    @Column(nullable = false)
    private YearMonth month;


    // Getters and Setters

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public YearMonth getMonth() {
        return month;
    }

    public void setMonth(YearMonth month) {
        this.month = month;
    }

    public BigDecimal getCurrentSpent() {
        return currentSpent;
    }

    public void setCurrentSpent(BigDecimal amount) {
        this.currentSpent = amount;
    }

     public BigDecimal getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(BigDecimal monthlyLimit ) {
        this.monthlyLimit = monthlyLimit;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
