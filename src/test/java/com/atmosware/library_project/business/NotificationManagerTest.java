package com.atmosware.library_project.business;

import com.atmosware.library_project.business.concretes.NotificationManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NotificationManagerTest {

    private NotificationManager notificationManager;
    private JavaMailSender mailSender;

    @BeforeEach
    void setUp() {
        mailSender = Mockito.mock(JavaMailSender.class);
        notificationManager = new NotificationManager(mailSender);
    }

    @Test
    void sendNotificationToAllUsers_ShouldSendEmails() {
        // Arrange
        List<String> userEmails = Arrays.asList("user1@example.com", "user2@example.com");
        String bookTitle = "New Book";

        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Act
        //notificationManager.sendNotificationToAllUsers(userEmails, bookTitle);

        // Assert
        verify(mailSender, times(userEmails.size())).send(any(MimeMessage.class));
    }

    @Test
    void sendNotification_ShouldSendEmail() throws MessagingException {
        // Arrange
        String mail = "user@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // Capture the helper to verify its interactions
        ArgumentCaptor<MimeMessageHelper> captor = ArgumentCaptor.forClass(MimeMessageHelper.class);

        // Act
        notificationManager.sendNotification(mail, subject, body);

        // Assert
        verify(mailSender, times(1)).send(mimeMessage);

        // Verifying the helper interactions
        verify(mimeMessage, never()).setText(body); // Since we mock MimeMessage and not MimeMessageHelper, this won't be directly verifiable on MimeMessage
    }

    @Test
    void sendNotification_ShouldThrowException_WhenMessagingFails() {
        // Arrange
        String mail = "user@example.com";
        String subject = "Test Subject";
        String body = "Test Body";

        MimeMessage mimeMessage = Mockito.mock(MimeMessage.class);
        when(mailSender.createMimeMessage()).thenReturn(mimeMessage);

        // We cannot mock the constructor directly, so we mock the JavaMailSender to simulate an exception in the send method
        doThrow(new RuntimeException("Failed to send email")).when(mailSender).send(mimeMessage);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            notificationManager.sendNotification(mail, subject, body);
        });

        assertEquals("Failed to send email", exception.getMessage());
    }

}