package com.employee.management.services;

import com.employee.management.entity.EmailDetails;
import com.employee.management.entity.Employee;
import com.employee.management.repo.EmployeeRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

@Service
public class ScheduleService {

    @Autowired
    private EmailServiceImpl emailService;

    @Autowired
    private EmployeeRepo employeeRepo;

    private static final Logger LOGGER = LoggerFactory.getLogger(ScheduleService.class);

    public void birthdayWishMail() {
        LocalDate today = LocalDate.now();
        Date sqlDate = Date.valueOf(today);
        List<Employee> employeeList = employeeRepo.getByDateOfBirth(sqlDate);
        if (!employeeList.isEmpty()) {
            employeeList.forEach(employeeDetails -> {
                EmailDetails emailDetails = new EmailDetails();
                emailDetails.setRecipient(employeeDetails.getEmail());
                emailDetails.setSubject("Happy Birthday " + employeeDetails.getName());

                // Read HTML template from resources
                String htmlTemplate;
                try {
                    InputStream inputStream = new ClassPathResource("templates/BirthdayWishTemplate.html").getInputStream();
                    htmlTemplate = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
                } catch (IOException e) {
                    LOGGER.error("Error reading email template", e);
                    return;
                }

                // Replace placeholder with recipient's name
                String htmlContent = htmlTemplate.replace("{{name}}", employeeDetails.getName());
                emailDetails.setMsgBody(htmlContent);

                // Send email
                emailService.sendTemplateMail(emailDetails);
            });
        }
    }
}
