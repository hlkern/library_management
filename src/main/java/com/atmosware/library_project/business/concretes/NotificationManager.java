package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationManager implements NotificationService {

    private final JavaMailSender mailSender;

    public void sendNotificationToAllUsers(List<String> userEmails, String bookTıtle) {
        String subject = "New book";
        String body = "Dear customer, now " + bookTıtle + " is avaliable in our library ";

        for (String email : userEmails) {
            sendNotification(email, subject, body);
        }
    }
    @Override
    public void sendNotification(String mail, String subject, String body) {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");

        try {
            helper.setText(body, true);
            helper.setTo(mail);
            helper.setSubject(subject);
            helper.setFrom("2e030996daeaa2");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }
}
