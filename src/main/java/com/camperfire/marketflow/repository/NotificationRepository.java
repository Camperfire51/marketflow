package com.camperfire.marketflow.repository;

import com.camperfire.marketflow.model.Notification;
import com.camperfire.marketflow.model.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("""
        SELECT n 
        FROM Notification n 
        WHERE (:userId IS NULL OR n.user.id = :userId)
          AND (:type IS NULL OR n.type = :type)
          AND (:read IS NULL OR n.read = :read)
        """)
    List<Notification> findNotifications(
            @Param("userId") Long userId,
            @Param("type") NotificationType type,
            @Param("read") Boolean read
    );
}
