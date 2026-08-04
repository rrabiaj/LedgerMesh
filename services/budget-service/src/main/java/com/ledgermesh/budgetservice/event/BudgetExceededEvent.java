package com.ledgermesh.budgetservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BudgetExceededEvent {
    
    private UUID budgetId;
    private UUID userId;
    private String category;
    private BigDecimal limitAmount;
    private BigDecimal spentAmount;
    private LocalDateTime timestamp;
    
}
