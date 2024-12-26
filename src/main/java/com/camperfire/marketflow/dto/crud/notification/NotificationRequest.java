package com.camperfire.marketflow.dto.crud.notification;

import com.camperfire.marketflow.model.NotificationType;
import com.camperfire.marketflow.validation.CreateRequest;
import com.camperfire.marketflow.validation.UpdateRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {

    @Null(groups = CreateRequest.class, message = "ID is not required for create request")
    @NotNull(groups = UpdateRequest.class, message = "ID is required for update request.")
    @Positive(message = "ID must be positive.")
    private Long id;

    @NotNull(message = "Notification type can not be null")
    private NotificationType type;

    @NotNull(message = "Message can not be null")
    private String message;
}
