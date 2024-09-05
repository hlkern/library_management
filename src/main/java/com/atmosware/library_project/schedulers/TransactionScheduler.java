package com.atmosware.library_project.schedulers;

import com.atmosware.library_project.business.abstracts.NotificationService;
import com.atmosware.library_project.dataAccess.TransactionRepository;
import com.atmosware.library_project.dataAccess.UserRepository;
import com.atmosware.library_project.entities.User;
import com.atmosware.library_project.entities.enums.BookStatus;
import com.atmosware.library_project.entities.enums.MembershipStatus;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import com.atmosware.library_project.entities.Transaction;

import java.time.LocalDateTime;
import java.util.List;

@Component
@AllArgsConstructor
public class TransactionScheduler {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkForOverdueBooks() {
        List<Transaction> transactions = transactionRepository.findAll();

        for (Transaction transaction : transactions) {
            if (transaction.getBookStatus() == BookStatus.BORROWED) {
                LocalDateTime dueDate = transaction.getBorrowDate().plusDays(30);
                if (LocalDateTime.now().isAfter(dueDate)) {
                    String email = transaction.getUser().getEmail();
                    String subject = "Overdue Book";
                    String body = "Dear customer, the return period for the book titled '" + transaction.getBooks().get(0).getTitle() + "' has expired. Please return it as soon as possible.";

                    notificationService.sendNotification(email, subject, body);
                }
            }
        }
    }
    @Scheduled(cron = "0 0 0 * * ?")
    public void checkForExpiredMemberships() {
        List<User> users = userRepository.findAll();

        for (User user : users) {
            if (user.getMembershipStatus() == MembershipStatus.ACTIVE || user.getEmailPermission()) {
                if (LocalDateTime.now().isAfter(user.getMembershipExpirationDate())) {
                    String email = user.getEmail();
                    String subject = "Membership Expiration Notice";
                    String body = "Dear customer, your membership has expired. Please renew your membership to continue enjoying our services.";

                    notificationService.sendNotification(email, subject, body);
                }
            }
        }
    }
}
