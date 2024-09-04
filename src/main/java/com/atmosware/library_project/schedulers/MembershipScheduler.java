package com.atmosware.library_project.schedulers;

import com.atmosware.library_project.business.abstracts.UserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MembershipScheduler {

    private final UserService userService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void updateExpiredMemberships() {
        userService.updateExpiredMemberships();
    }
}
