package com.ledgermesh.notificationservice.controller;

import com.ledgermesh.notificationservice.model.Notification;
import com.ledgermesh.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping
    public ResponseEntity<List<Notification>> getNotifications(@RequestParam UUID userId) {
        return ResponseEntity.ok(notificationService.getNotificationsByUserId(userId));
    }
}

