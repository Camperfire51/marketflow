package com.camperfire.marketflow.dto.response;

import com.camperfire.marketflow.model.NotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    @NotNull(message = "Notification type can not be null")
    private NotificationType type;

    @NotNull(message = "Message can not be null")
    private String message;
}
