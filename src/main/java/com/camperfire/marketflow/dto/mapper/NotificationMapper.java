package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.crud.notification.NotificationRequest;
import com.camperfire.marketflow.dto.mapper.utility.NotificationMapperUtility;
import com.camperfire.marketflow.dto.response.NotificationResponse;
import com.camperfire.marketflow.model.Notification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {NotificationMapperUtility.class})
public interface NotificationMapper {

    Notification toEntity(NotificationRequest dto);

    NotificationResponse toResponse(Notification entity);

    List<NotificationResponse> toResponseList(List<Notification> entities);
}
