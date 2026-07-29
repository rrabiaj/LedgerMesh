package com.ledgermesh.transactionservice.controller;
import com.ledgermesh.transactionservice.dto.TransactionRequestDTO;
import com.ledgermesh.transactionservice.dto.TransactionResponseDTO;
import com.ledgermesh.transactionservice.service.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
public class TransactionController {

    private final TransactionService transactionService;

    @PostMapping
    public ResponseEntity<TransactionResponseDTO> createTransaction(@Valid @RequestBody TransactionRequestDTO request) {
        TransactionResponseDTO response = transactionService.createTransaction(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<TransactionResponseDTO>> getTrasactions(
        @RequestParam(required = false) UUID accountId,
        @RequestParam(required = false) UUID userId){

            if(accountId != null){
                return ResponseEntity.ok(transactionService.getTransactionsByAccountId(accountId));
            }

            if(userId != null){
                return ResponseEntity.ok(transactionService.getTransactionByUserId(userId));
            }
            return ResponseEntity.badRequest().build();
        }
}   
    