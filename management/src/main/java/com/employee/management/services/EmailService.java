package com.employee.management.services;

import com.employee.management.entity.EmailDetails;
import org.springframework.stereotype.Service;

@Service
public interface EmailService {
    void sendSimpleMail(EmailDetails emailDetails);
    String sendMailWithAttachments(EmailDetails emailDetails);
}
