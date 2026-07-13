package com.ledgermesh.accountservice.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import com.ledgermesh.accountservice.model.AccountType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountResponseDTO {
    private UUID id;
    private UUID userId;
    private String accountName;
    private AccountType accountType;
    private BigDecimal balance;
    private String currency;
    private Instant createdAt;
    private Instant updatedAt;
    
}
