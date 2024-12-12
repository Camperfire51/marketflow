package com.camperfire.marketflow.dto.request;

import com.camperfire.marketflow.model.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {
    @NotNull(message = "User ID can not be null")
    private Long userId;

    @NotNull(message = "Notification type can not be null")
    private NotificationType type;

    @NotNull(message = "Message can not be null")
    private String message;
}
