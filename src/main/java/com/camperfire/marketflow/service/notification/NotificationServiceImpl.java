package com.camperfire.marketflow.service.notification;

import com.camperfire.marketflow.dto.mapper.NotificationMapper;
import com.camperfire.marketflow.dto.request.NotificationRequest;
import com.camperfire.marketflow.model.Notification;
import com.camperfire.marketflow.model.NotificationType;
import com.camperfire.marketflow.repository.NotificationRepository;
import com.camperfire.marketflow.specification.NotificationSpecification;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    public NotificationServiceImpl(KafkaTemplate<String, Object> kafkaTemplate, NotificationRepository notificationRepository, NotificationMapper notificationMapper) {
        this.notificationRepository = notificationRepository;
        this.notificationMapper = notificationMapper;
    }

    @Override
    public List<Notification> getNotifications(Long userId, NotificationType type, Boolean isRead) {
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

    @Override
    public Notification createNotification(NotificationRequest notificationRequest) {
        Notification notification = notificationMapper.toEntity(notificationRequest);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification readNotification(Long notificationId) {
        return notificationRepository.findById(notificationId).orElseThrow();
    }

    @Override
    public Notification updateNotification(Long notificationId, NotificationRequest notificationRequest) {
        Notification notification = notificationMapper.toEntity(notificationRequest);
        notification.setId(notificationId);
        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
