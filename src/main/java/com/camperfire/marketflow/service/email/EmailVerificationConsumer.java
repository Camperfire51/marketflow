package com.camperfire.marketflow.service.email;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationConsumer {

    private final JavaMailSender javaMailSender;

    public EmailVerificationConsumer(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @KafkaListener(topics = "email-verification-topic", groupId = "email-verification-group")
    public void listen(String message) {
        String[] parts = message.split("\\|");
        String email = parts[0];
        String verificationLink = parts[1];

        // Send the email with the verification link
        sendVerificationEmail(email, verificationLink);
    }

    private void sendVerificationEmail(String email, String verificationLink) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom("camperfire11@gmail.com");
        msg.setTo(email);
        msg.setSubject("Please verify your email address");
        msg.setText("Click the following link to verify your email: " + verificationLink);
        javaMailSender.send(msg);
    }
}

