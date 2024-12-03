package com.camperfire.marketflow.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthUserRequestDTO {
    private String username;
    private String password;
}
