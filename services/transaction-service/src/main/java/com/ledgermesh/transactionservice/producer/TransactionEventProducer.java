package com.ledgermesh.transactionservice.producer;
import com.ledgermesh.transactionservice.event.TransactionCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionEventProducer {
    
    private final KafkaTemplate<String, TransactionCreatedEvent> kafkaTemplate;
    private static final String TOPIC = "transaction-events";

    public void sendTransactionCreatedEvent(TransactionCreatedEvent event) {
        log.info("Sending transaction created event to Kafka: {}", event);

        kafkaTemplate.send(TOPIC, event.getTransactionId().toString(), event)
                .whenComplete((result, ex) -> {
                    if (ex == null) {
                        log.info("Message sent successfully to topic: {}, partition: {}, offset: {}", 
                             TOPIC, result.getRecordMetadata().partition(), result.getRecordMetadata().offset());
                    } else {
                        log.error("Failed to send message to Kafka", ex);  
                    }
                });
    }
}
