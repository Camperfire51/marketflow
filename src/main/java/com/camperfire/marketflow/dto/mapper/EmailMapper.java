package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.crud.email.EmailMessageRequest;
import com.camperfire.marketflow.model.EmailMessage;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    EmailMessage toEntity(EmailMessageRequest emailMessageRequest);
}
