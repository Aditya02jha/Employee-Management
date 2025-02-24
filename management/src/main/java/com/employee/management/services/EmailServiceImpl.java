package com.employee.management.services;

import com.employee.management.entity.EmailDetails;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
@Service
public class EmailServiceImpl implements EmailService{

    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String sender;

    @Override
    public void sendSimpleMail(EmailDetails emailDetails){
        try{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailDetails.getRecipient());
            message.setSubject(emailDetails.getSubject());
            message.setText(emailDetails.getMsgBody());
            message.setFrom(sender);
            javaMailSender.send(message);
        }catch (Exception e){
        }
    }
    public void sendTemplateMail(EmailDetails emailDetails) throws MailSendException {
        try {
            // Create a new MimeMessage
            MimeMessage message = javaMailSender.createMimeMessage();

            // Use MimeMessageHelper for better handling
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(emailDetails.getRecipient());  // Set recipient
            helper.setFrom(sender);  // Set sender
            helper.setSubject(emailDetails.getSubject());  // Set subject
            helper.setText(emailDetails.getMsgBody(), true);  // Enable HTML

            // Send the email
            javaMailSender.send(message);

            System.out.println("Email sent successfully!");
        } catch (Exception e) {
//            e.printStackTrace();
//            throw new MessagingException("Failed to send email", e);
        }
    }

    @Override
    public String sendMailWithAttachments(EmailDetails emailDetails){
        // Creating a MimeMessage
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try{
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage , true);
            mimeMessageHelper.setFrom(sender);
            mimeMessageHelper.setText(emailDetails.getMsgBody());
            mimeMessageHelper.setSubject(emailDetails.getSubject());
            //get file form file system
            FileSystemResource fileSystemResource = new FileSystemResource(new File(emailDetails.getAttachment()));
            mimeMessageHelper.addAttachment(fileSystemResource.getFilename() , fileSystemResource);

            javaMailSender.send(mimeMessage);
            return "Mail with attachment successFully sent";
        }catch (Exception E){
            return "Error while Sending Mail with attachments";
        }
    }

}
