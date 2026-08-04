package com.ledgermesh.budgetservice.producer;
import com.ledgermesh.budgetservice.event.BudgetExceededEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BudgetEventProducer {

    private final KafkaTemplate<String, BudgetExceededEvent> kafkaTemplate;
    private static final String TOPIC = "budget-exceeded-events";

    @SuppressWarnings("null")
    public void sendBudgetExceededEvent(BudgetExceededEvent event) {
        log.info("Sending budget exceeded event to Kafka: {}", event);

        kafkaTemplate.send(TOPIC, event.getBudgetId().toString(), event)
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
