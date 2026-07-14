package com.ledgermesh.accountservice.mapper;

import org.springframework.stereotype.Component;

import com.ledgermesh.accountservice.Account;
import com.ledgermesh.accountservice.dto.AccountRequestDTO;
import com.ledgermesh.accountservice.dto.AccountResponseDTO;

@Component
public class AccountMapper {
    public Account toEntity(AccountRequestDTO request) {
        return Account.builder()
                .userId(request.getUserId())
                .accountName(request.getAccountName())
                .accountType(request.getAccountType())
                .balance(request.getBalance())
                .currency(request.getCurrency() != null ? request.getCurrency() : "EUR")
                .build();
    }
    
    public AccountResponseDTO toResponse(Account account) {
        return AccountResponseDTO.builder()
                .id(account.getId())
                .userId(account.getUserId())
                .accountName(account.getAccountName())
                .accountType(account.getAccountType())
                .balance(account.getBalance())
                .currency(account.getCurrency())
                .createdAt(account.getCreatedAt())
                .updatedAt(account.getUpdatedAt())
                .build();
    }
}
