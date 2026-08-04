package com.ledgermesh.notificationservice.consumer;

import com.ledgermesh.notificationservice.event.BudgetExceededEvent;
import com.ledgermesh.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class BudgetExceededEventConsumer {

    private final NotificationService notificationService;

	@KafkaListener(topics = "budget-exceeded-events",groupId = "notification-service-group")    
	
	public void handleBudgetExceeded(BudgetExceededEvent event) {
        log.info("Received budget exceeded event for user {}: {}", event.getUserId(), event.getCategory());
        notificationService.handleBudgetExceeded(event);
    }
}
