package com.camperfire.marketflow.service.kafka;

import com.camperfire.marketflow.model.Email;
import com.camperfire.marketflow.model.Notification;

public interface KafkaService {

    void consumeNotification(Notification notification);

    void consumeEmail(Email email);

    void produceNotification(Notification notification);

    void produceEmail(Email email);
}
