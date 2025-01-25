package com.example.library_api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    // JavaMailSender is used to send emails
    private final JavaMailSender mailSender;

    // The email address that will be used to send the email, injected from application properties
    @Value("${spring.mail.username}")
    private String fromEmail;

    // Constructor to initialize JavaMailSender
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    // Method to send an email with provided recipient, subject, and body
    public void sendEmail(String toEmail, String subject, String body) {
        // Create a new email message
        SimpleMailMessage message = new SimpleMailMessage();
        
        // Set the sender's email address
        message.setFrom(fromEmail);
        
        // Set the recipient's email address
        message.setTo(toEmail);
        
        // Set the email's subject
        message.setSubject(subject);
        
        // Set the body/content of the email
        message.setText(body);
        
        // Send the email using the JavaMailSender
        mailSender.send(message);
    }
}
