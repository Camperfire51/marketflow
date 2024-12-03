package com.camperfire.marketflow.dto.response;

import com.camperfire.marketflow.model.Address;
import com.camperfire.marketflow.model.UserRole;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class RegisterResponse {
    protected Long id;
    protected String username;
    protected String email;
    protected LocalDateTime createdDate;
    protected Address address;
    protected UserRole userRole;
}
