package com.atmosware.library_project.business.concretes;

import com.atmosware.library_project.business.abstracts.NotificationService;
import lombok.AllArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class NotificationManager implements NotificationService {

    private final JavaMailSender mailSender;

    @Override
    public void sendNotificationToAllUsers(List<String> userEmails, String bookTitle, String bookAuthor) {
        String subject = "New Book Alert";
        String body = "Dear customer, now '" + bookTitle + "' by "+ bookAuthor +" is avaliable in our library ";

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
            helper.setFrom("librarymanagement37@outlook.com");
            mailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }



}
