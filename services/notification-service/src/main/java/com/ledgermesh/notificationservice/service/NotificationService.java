package com.ledgermesh.notificationservice.service;

import com.ledgermesh.notificationservice.event.BudgetExceededEvent;
import com.ledgermesh.notificationservice.model.Notification;
import com.ledgermesh.notificationservice.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    
    private final NotificationRepository notificationRepository;

    @Transactional
    public void handleBudgetExceeded(BudgetExceededEvent event) {
        String message = String.format("Budget exceeded for category '%s'. Limit: %s, Spent: %s", 
                                        event.getCategory(), 
                                        event.getLimitAmount(), 
                                        event.getSpentAmount());
        
        Notification notification = Notification.builder()
                .userId(event.getUserId())
                .category(event.getCategory())
                .message(message)
                .sent(true)
                .build();
        
        notificationRepository.save(notification);

        // Simulim i "dërgimit" — më vonë këtu do të lidhet email real (Mailhog)
        log.info("📧 [SIMULATED EMAIL] To user {}: {}", event.getUserId(), message);
    }

    @Transactional(readOnly = true)
    public List<Notification> getNotificationsByUserId(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }
}
