package com.camperfire.marketflow.service.notification;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceConsumer {

    @KafkaListener
    public void listen() {}
}
