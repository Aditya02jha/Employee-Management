package com.employee.management.components;

import com.employee.management.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    @Autowired
    private ScheduleService demoService;

    @Scheduled(cron = "*/5 * * * * *") // Cron expression for running every minute
    public void execute() {
        demoService.dummyMethod();
    }
}
