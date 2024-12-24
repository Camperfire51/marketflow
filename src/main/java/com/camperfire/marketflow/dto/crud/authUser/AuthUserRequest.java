package com.camperfire.marketflow.dto.crud.authUser;

import com.camperfire.marketflow.model.UserRole;
import com.camperfire.marketflow.validation.CreateRequest;
import com.camperfire.marketflow.validation.UpdateRequest;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUserRequest {

    @Null(groups = UpdateRequest.class, message = "You cannot change your username.")
    @NotNull(groups = CreateRequest.class, message = "Password is required for create request.")
    @Length(min = 5, max = 32, message = "Username must be between 5-32 characters")
    private String username;

    @NotNull(groups = CreateRequest.class, message = "Password is required for create request.")
    @Length(min = 5, max = 32, message = "Password must be between 5-32 characters")
    private String password;

    @Null(groups = UpdateRequest.class, message = "You cannot change your email address directly.")
    @NotNull(groups = CreateRequest.class,message = "Email is required for create request.")
    private String email;

    @Null(groups = UpdateRequest.class, message = "You cannot change your user role.")
    @NotNull(groups = CreateRequest.class, message = "User role is required for create request.")
    private UserRole role;

}
