package com.ledgermesh.budgetservice.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransactionCreatedEvent {

    private UUID transactionId;
    private UUID accountId;
    private UUID userId;
    private BigDecimal amount;
    private String description;
    private String category;
    private LocalDateTime timestamp;
}