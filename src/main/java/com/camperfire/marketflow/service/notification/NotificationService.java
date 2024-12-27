package com.camperfire.marketflow.service.notification;

import com.camperfire.marketflow.dto.crud.notification.NotificationRequest;
import com.camperfire.marketflow.model.Notification;
import com.camperfire.marketflow.model.NotificationType;

import java.util.List;

public interface NotificationService {

    List<Notification> getNotifications(Long userId, NotificationType type, Boolean isRead);

    List<Notification> getAuthenticatedNotifications(NotificationType type, Boolean isRead);

    Notification createNotification(NotificationRequest request);

    Notification readNotification(Long id);

    Notification updateNotification(NotificationRequest request);

    void deleteNotification(Long id);
}
