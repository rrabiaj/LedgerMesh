package com.ledgermesh.transactionservice.service;
import com.ledgermesh.transactionservice.dto.TransactionRequestDTO;
import com.ledgermesh.transactionservice.dto.TransactionResponseDTO;
import com.ledgermesh.transactionservice.exception.TransactionNotFoundException;
import com.ledgermesh.transactionservice.mapper.TransactionMapper;
import com.ledgermesh.transactionservice.model.Transaction;
import com.ledgermesh.transactionservice.producer.TransactionEventProducer;
import com.ledgermesh.transactionservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final TransactionEventProducer transactionEventProducer;

    @Transactional
    public TransactionResponseDTO createTransaction(TransactionRequestDTO request) {
        Transaction transaction = transactionMapper.toEntity(request);
        Transaction saved = transactionRepository.save(transaction);
        transactionEventProducer.sendTransactionCreatedEvent(transactionMapper.toEvent(saved));
        return transactionMapper.toResponse(saved);
    }

     @Transactional(readOnly = true)
    public TransactionResponseDTO getTransactionById(UUID id) {
        Transaction transaction = transactionRepository.findById(id)
                .orElseThrow(() -> new TransactionNotFoundException(id));
        return transactionMapper.toResponse(transaction);
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getTransactionsByAccountId(UUID accountId) {
        return transactionRepository.findByAccountId(accountId).stream()
                .map(transactionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<TransactionResponseDTO> getTransactionByUserId(UUID userId) {
            return transactionRepository.findByUserId(userId).stream()
                                        .map(transactionMapper::toResponse)
                                        .collect(Collectors.toList());
    }
    
}
