package com.ledgermesh.notificationservice.event;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
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
