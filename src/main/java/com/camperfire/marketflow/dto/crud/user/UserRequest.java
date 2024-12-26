package com.camperfire.marketflow.dto.crud.user;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.model.UserRole;
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
public class UserRequest {

    @Null(groups = CreateRequest.class, message = "ID is not required for create request")
    @NotNull(groups = UpdateRequest.class, message = "ID is required for update request.")
    @Positive(message = "ID must be positive.")
    private Long id;

    @NotNull(groups = CreateRequest.class, message = "Address is required for create request.")
    private Address address;

    @Null(groups = CreateRequest.class, message = "Auth user id is not required for create request.")
    private Long authUserId;

    @Null(groups = UpdateRequest.class, message = "You cannot change your user role.")
    @NotNull(groups = CreateRequest.class, message = "User role is required for create request.")
    private UserRole role;
}
