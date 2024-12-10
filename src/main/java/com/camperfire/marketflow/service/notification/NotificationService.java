package com.camperfire.marketflow.service.notification;

import com.camperfire.marketflow.model.Notification;
import com.camperfire.marketflow.model.NotificationType;

public interface NotificationService {

    void getNotifications(Long userId, NotificationType type, Boolean isRead);


    Notification createNotification(Notification notification);

    Notification readNotification(Long notificationId);
}
