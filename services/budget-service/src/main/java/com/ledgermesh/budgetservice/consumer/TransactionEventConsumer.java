package com.ledgermesh.budgetservice.consumer;

import com.ledgermesh.budgetservice.event.TransactionCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TransactionEventConsumer {

    @KafkaListener(topics = "transaction-events", groupId = "budget-service-group")

    public void consumeTransactionCreatedEvent(TransactionCreatedEvent event){
        
        log.info("Received TransactionCreatedEvent: {}", event);
        
        log.info("Evaluating budget for userId={} , category={} and amount={} ", event.getUserId(), event.getCategory(), event.getAmount());
    }
}
