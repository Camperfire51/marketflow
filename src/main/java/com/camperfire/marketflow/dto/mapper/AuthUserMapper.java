package com.camperfire.marketflow.dto.mapper;

import com.camperfire.marketflow.dto.crud.authUser.AuthUserRequest;
import com.camperfire.marketflow.dto.crud.authUser.AuthUserResponse;
import com.camperfire.marketflow.model.AuthUser;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface AuthUserMapper {

    AuthUser toEntity(AuthUserRequest request);

    AuthUserResponse toResponse(AuthUser entity);
}
