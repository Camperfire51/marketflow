package com.camperfire.marketflow.dto.crud.user;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.validation.CreateRequest;
import jakarta.validation.constraints.NotNull;

public class UserRequest {

    @NotNull(groups = CreateRequest.class, message = "Address is required for create request.")
    private Address address;
}
