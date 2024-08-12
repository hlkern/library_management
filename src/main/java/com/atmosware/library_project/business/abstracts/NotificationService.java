package com.atmosware.library_project.business.abstracts;

public interface NotificationService {
    void sendNotification(String to, String subject, String body);
}
