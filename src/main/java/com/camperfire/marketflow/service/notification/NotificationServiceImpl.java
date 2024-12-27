package com.camperfire.marketflow.service.notification;

import com.camperfire.marketflow.dto.crud.notification.NotificationRequest;
import com.camperfire.marketflow.dto.mapper.NotificationMapper;
import com.camperfire.marketflow.model.AuthUser;
import com.camperfire.marketflow.model.Notification;
import com.camperfire.marketflow.model.NotificationType;
import com.camperfire.marketflow.model.UserPrincipal;
import com.camperfire.marketflow.model.user.User;
import com.camperfire.marketflow.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public List<Notification> getNotifications(Long userId, NotificationType type, Boolean isRead) {
        return notificationRepository.findNotifications(userId, type, isRead);
    }

    @Override
    public List<Notification> getAuthenticatedNotifications(NotificationType type, Boolean isRead) {

        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AuthUser authUser = principal.getAuthUser();

        User user = authUser.getUser();

        return notificationRepository.findNotifications(user.getId(), type, isRead);
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
