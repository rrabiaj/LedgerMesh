package com.ledgermesh.accountservice.dto;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AccountRequestDTO {
    
    @NotNull(message = "User ID cannot be null")
    private UUID userId;

    @NotNull(message = "Account name cannot be null")
    @Size(min = 1, max = 100)
    private String accountName;

    @NotNull(message = "Account type cannot be null")
    private String accountType;

    @NotNull(message = "Balance cannot be null")
    @PositiveOrZero(message = "Balance must be zero or positive")
    private BigDecimal balance;

    @Size(min = 3, max = 3, message = "Currency must be a 3-character code")
    private String currency;

}
