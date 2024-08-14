package com.atmosware.library_project.api.controllers;

import com.atmosware.library_project.business.abstracts.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@AllArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send-to-all")
    public void sendNotificationToAllUsers(@RequestParam List<String> userEmails, @RequestParam String bookTitle) {
        notificationService.sendNotificationToAllUsers(userEmails, bookTitle);
    }

    @PostMapping("/send")
    public void sendNotification(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        notificationService.sendNotification(to, subject, body);
    }
}
