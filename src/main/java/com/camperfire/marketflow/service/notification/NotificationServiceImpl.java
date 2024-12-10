package com.camperfire.marketflow.service.notification;

import com.camperfire.marketflow.model.Notification;
import com.camperfire.marketflow.model.NotificationType;
import com.camperfire.marketflow.model.Product;
import com.camperfire.marketflow.repository.NotificationRepository;
import com.camperfire.marketflow.specification.NotificationSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;

public class NotificationServiceImpl implements NotificationService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC = "restock-alarm-topic";
    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(KafkaTemplate<String, Object> kafkaTemplate, NotificationRepository notificationRepository) {

        this.kafkaTemplate = kafkaTemplate;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void getNotifications(Long userId, NotificationType type, Boolean isRead) {
        Specification<Notification> spec = Specification.where(null);

        if (userId != null) {
            spec = spec.and(NotificationSpecification.hasUserById(userId));
        }

        if (type != null) {
            spec = spec.and(NotificationSpecification.hasType(type));
        }

        if (isRead != null) {
            spec = spec.and(NotificationSpecification.isRead(isRead));
        }
        return notificationRepository.findAll(spec);
    }
}
