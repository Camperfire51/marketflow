package com.camperfire.marketflow.dto.request;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.model.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegisterRequest {
    private String email;
    private Address address;
    private UserRole userRole;
    private String username;
    private String password;

}
