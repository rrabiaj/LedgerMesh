package com.ledgermesh.budgetservice.consumer;

import com.ledgermesh.budgetservice.event.TransactionCreatedEvent;
import com.ledgermesh.budgetservice.service.BudgetService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.ledgermesh.budgetservice.dto.BudgetEvaluationRequestDTO;

@Component
public class TransactionEventConsumer {

    private static final Logger logger =
            LoggerFactory.getLogger(TransactionEventConsumer.class);

    private final BudgetService budgetService;

    public TransactionEventConsumer(BudgetService budgetService) {
        this.budgetService = budgetService;
    }

    @KafkaListener(
            topics = "transaction-events",
            groupId = "budget-service-group"
    )
    public void consumeTransactionCreatedEvent(
            TransactionCreatedEvent event) {

        logger.info(
                "Budget Service received transaction event: {}",
                event
        );

        logger.info(
                "Evaluating budget for userId={}, category={}, amount={}",
                event.getUserId(),
                event.getCategory(),
                event.getAmount()
        );

        budgetService.evaluateTransaction(
        BudgetEvaluationRequestDTO.builder()
                .userId(event.getUserId())
                .category(event.getCategory())
                .amount(event.getAmount())
                .build()
	);
    }
}