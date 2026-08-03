package com.ledgermesh.budgetservice.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetEvaluationResponseDTO {
    
    private UUID budgetId;
    private UUID userId;
    private String category;
    private BigDecimal limitAmount;
    private BigDecimal previousSpentAmount;
    private BigDecimal currentSpentAmount;
    private BigDecimal remainingAmount;
    private boolean exceeded;    
}
