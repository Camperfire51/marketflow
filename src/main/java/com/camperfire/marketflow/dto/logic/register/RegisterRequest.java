package com.camperfire.marketflow.dto.logic.register;

import com.camperfire.marketflow.dto.crud.authUser.AuthUserRequest;
import com.camperfire.marketflow.dto.crud.user.UserRequest;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RegisterRequest {

    @NotNull
    private AuthUserRequest authUserRequest;

    @NotNull
    private UserRequest userRequest;
}
