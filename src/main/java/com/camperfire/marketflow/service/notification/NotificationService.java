package com.camperfire.marketflow.service.notification;

import com.camperfire.marketflow.dto.request.NotificationRequest;
import com.camperfire.marketflow.model.Notification;
import com.camperfire.marketflow.model.NotificationType;

import java.util.List;

public interface NotificationService {

    List<Notification> getNotifications(Long userId, NotificationType type, Boolean isRead);

    Notification createNotification(NotificationRequest notificationRequest);

    Notification readNotification(Long notificationId);

    Notification updateNotification(Long notificationId, NotificationRequest notificationRequest);

    void deleteNotification(Long notificationId);
}
