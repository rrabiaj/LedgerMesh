package com.ledgermesh.budgetservice.mapper;

import com.ledgermesh.budgetservice.dto.BudgetRequestDTO;
import com.ledgermesh.budgetservice.dto.BudgetResponseDTO;
import com.ledgermesh.budgetservice.model.Budget;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;


@Component
public class BudgetMapper {
    
     public Budget toEntity(BudgetRequestDTO request) {
        return Budget.builder()
                .userId(request.getUserId())
                .category(request.getCategory())
                .limitAmount(request.getLimitAmount())
                .spentAmount(BigDecimal.ZERO)
                .build();
    }
    
    public BudgetResponseDTO toResponse(Budget budget) {
        return BudgetResponseDTO.builder()
                .id(budget.getId())
                .userId(budget.getUserId())
                .category(budget.getCategory())
                .limitAmount(budget.getLimitAmount())
                .spentAmount(budget.getSpentAmount())
                .createdAt(budget.getCreatedAt())
                .updatedAt(budget.getUpdatedAt())
                .build();
    }

}
