package com.ledgermesh.transactionservice.dto;

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
public class TransactionResponseDTO {

    private UUID id;

    private UUID accountId;

    private UUID userId;

    private BigDecimal amount;

    private String description;

    private String category;

    private Instant createdAt;  
    
}
