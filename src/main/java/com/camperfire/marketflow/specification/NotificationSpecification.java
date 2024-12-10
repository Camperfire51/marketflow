package com.camperfire.marketflow.specification;

import com.camperfire.marketflow.model.Notification;
import com.camperfire.marketflow.model.NotificationType;
import org.springframework.data.jpa.domain.Specification;

public class NotificationSpecification {

    public static Specification<Notification> hasUserById(Long userId) {
        return (root, query, criteriaBuilder) ->
                userId == null ? null : criteriaBuilder.equal(root.get("vendor").get("id"), userId);
    }

    public static Specification<Notification> hasType(NotificationType type) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("type"), type);
    }

    public static Specification<Notification> isRead(Boolean isRead) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isRead"), isRead);
    }
}
