package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.request.AuthUserRequestDTO;
import com.camperfire.marketflow.dto.response.AuthUserResponseDTO;
import com.camperfire.marketflow.model.AuthUser;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface AuthUserMapper {

    AuthUser toEntity(AuthUserRequestDTO dto);

    AuthUserResponseDTO toResponse(AuthUser entity);
}
