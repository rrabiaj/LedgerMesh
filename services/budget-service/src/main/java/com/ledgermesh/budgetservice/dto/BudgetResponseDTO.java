package com.ledgermesh.budgetservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder    
public class BudgetResponseDTO {

    private UUID id;
    private UUID userId;
    private String category;
    private BigDecimal limitAmount;
    private BigDecimal spentAmount;
    private Instant createdAt;
    private Instant updatedAt;
    
}
