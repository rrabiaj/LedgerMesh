package com.ledgermesh.budgetservice.dto;

import java.math.BigDecimal;

public class CreateBudgetRequestDTO {

    private String category;
    private BigDecimal monthlyLimit;
    
    public CreateBudgetRequestDTO() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public BigDecimal getMonthlyLimit() {
        return monthlyLimit;
    }

    public void setMonthlyLimit(BigDecimal monthlyLimit) {
        this.monthlyLimit = monthlyLimit;
    }

}
