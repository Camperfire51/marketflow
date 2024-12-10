package com.camperfire.marketflow.service.kafka;

import com.camperfire.marketflow.model.Email;
import com.camperfire.marketflow.model.Notification;
import com.camperfire.marketflow.service.email.EmailService;
import com.camperfire.marketflow.service.notification.NotificationService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;

public class KafkaServiceImpl implements KafkaService {

    private final EmailService emailService;
    private final NotificationService notificationService;

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final String emailTopic;
    private final String notificationTopic;

    public KafkaServiceImpl(EmailService emailService, NotificationService notificationService, KafkaTemplate<String, Object> kafkaTemplate,
                            @Value("${kafka.topics.email-topic}") String emailTopic,
                            @Value("${kafka.topics.notification-topic}") String notificationTopic) {
        this.emailService = emailService;
        this.notificationService = notificationService;

        this.kafkaTemplate = kafkaTemplate;
        this.emailTopic = emailTopic;
        this.notificationTopic = notificationTopic;
    }

    @Override
    @KafkaListener(topics = "${kafka.topics.notification-topic}", groupId = "email-group")
    public void consumeNotification(Notification notification) {

    }

    @Override
    @KafkaListener(topics = "${kafka.topics.email-topic}", groupId = "email-group")
    public void consumeEmail(Email email) {
        emailService
    }

    @Override
    public void produceNotification(Notification notification) {
        kafkaTemplate.send(notificationTopic, notification);
    }

    @Override
    public void produceEmail(Email email) {
        kafkaTemplate.send(emailTopic, email);
    }
}
