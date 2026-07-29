package com.ledgermesh.transactionservice.mapper;
import com.ledgermesh.transactionservice.dto.TransactionRequestDTO;
import com.ledgermesh.transactionservice.dto.TransactionResponseDTO;
import com.ledgermesh.transactionservice.event.TransactionCreatedEvent;
import com.ledgermesh.transactionservice.model.Transaction;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Component
public class TransactionMapper {

    public Transaction toEntity(TransactionRequestDTO request){
        return Transaction.builder()
                .accountId(request.getAccountId())
                .userId(request.getUserId())
                .amount(request.getAmount())
                .description(request.getDescription())
                .category(request.getCategory())
                .build();
    }

    public TransactionResponseDTO toResponse(Transaction transaction){
        return TransactionResponseDTO.builder()
                .id(transaction.getId())
                .accountId(transaction.getAccountId())
                .userId(transaction.getUserId())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .category(transaction.getCategory())
                .createdAt(transaction.getCreatedAt())
                .build();
    }
    
    public TransactionCreatedEvent toEvent(Transaction transaction){
        return TransactionCreatedEvent.builder()
                .transactionId(transaction.getId())
                .accountId(transaction.getAccountId())
                .userId(transaction.getUserId())
                .amount(transaction.getAmount())
                .description(transaction.getDescription())
                .category(transaction.getCategory())
                .timestamp(LocalDateTime.ofInstant(transaction.getCreatedAt(), ZoneOffset.UTC))
                .build();
    }
}
