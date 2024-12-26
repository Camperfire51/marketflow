package com.camperfire.marketflow.service.authUser;

import com.camperfire.marketflow.dto.crud.authUser.AuthUserRequest;
import com.camperfire.marketflow.dto.logic.login.LoginRequest;
import com.camperfire.marketflow.dto.logic.register.RegisterRequest;
import com.camperfire.marketflow.model.AuthUser;

public interface AuthUserService {

    String register(RegisterRequest request);

    String login(LoginRequest request);

    boolean verifyEmail(String token);

    AuthUser createAuthUser(AuthUserRequest request);

    AuthUser readAuthUser(Long id);

    AuthUser updateAuthUser(AuthUserRequest request);

    void deleteAuthUser(Long id);
}
