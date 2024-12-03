package com.camperfire.marketflow.service.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "email-verification-topic";

    @Autowired
    public EmailVerificationProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendVerificationEmail(String email, String verificationLink) {
        // Sending a message to the Kafka topic
        String message = email + "|" + verificationLink; // Concatenate the email and link for simplicity
        kafkaTemplate.send(TOPIC, message);
    }
}
