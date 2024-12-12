package com.camperfire.marketflow.service.email;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceConsumer {

    private final JavaMailSender javaMailSender;

    public EmailServiceConsumer(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }


}

