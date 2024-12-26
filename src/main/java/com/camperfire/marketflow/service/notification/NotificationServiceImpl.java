package com.camperfire.marketflow.service.notification;

import com.camperfire.marketflow.dto.mapper.NotificationMapper;
import com.camperfire.marketflow.dto.crud.notification.NotificationRequest;
import com.camperfire.marketflow.model.EmailMessage;
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
    public Notification createNotification(NotificationRequest request) {
        Notification notification = notificationMapper.toEntity(request);
        return notificationRepository.save(notification);
    }

    @Override
    public Notification readNotification(Long id) {
        return notificationRepository.findById(id).orElseThrow();
    }

    @Override
    public Notification updateNotification(NotificationRequest request) {
        Notification notification = notificationRepository.findById(request.getId()).orElseThrow();

        //TODO: Implement update logic.

        return notificationRepository.save(notification);
    }

    @Override
    public void deleteNotification(Long id) {
        Notification notification = notificationRepository.findById(id).orElseThrow();

        notificationRepository.delete(notification);
    }
}
