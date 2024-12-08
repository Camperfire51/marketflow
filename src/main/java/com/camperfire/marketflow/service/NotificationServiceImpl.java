package com.camperfire.marketflow.service;

import org.springframework.kafka.core.KafkaTemplate;

public class NotificationServiceImpl implements NotificationService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "restock-alarm-topic";

    public NotificationServiceImpl(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendRestockAlarmNotification(Long productId) {
        kafkaTemplate.send(TOPIC, productId);
    }
}
