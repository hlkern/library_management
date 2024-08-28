package com.atmosware.library_project.business.abstracts;

import java.util.List;

public interface NotificationService {
    void sendNotificationToAllUsers(List<String> userEmails, String bookTitle, String bookAuthor);
    void sendNotification(String to, String subject, String body);
}
