package com.camperfire.marketflow.service;

import com.camperfire.marketflow.dto.logic.login.LoginRequest;
import com.camperfire.marketflow.dto.request.RegisterRequest;
import com.camperfire.marketflow.dto.response.LoginResponse;
import com.camperfire.marketflow.model.*;
import com.camperfire.marketflow.repository.AuthUserRepository;
import com.camperfire.marketflow.repository.user.CustomerRepository;
import com.camperfire.marketflow.repository.user.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthUserService {

    private final AuthUserRepository authUserRepository;

    private final CustomerRepository customerRepository;
    private final VendorRepository vendorRepository;

    private final JWTService jwtService;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder encoder;

    @Autowired
    public AuthUserService(AuthUserRepository authUserRepository, CustomerRepository customerRepository, VendorRepository vendorRepository, JWTService jwtService, AuthenticationManager authManager) {
        this.authUserRepository = authUserRepository;

        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;

        this.jwtService = jwtService;
        this.authManager = authManager;
        encoder = new BCryptPasswordEncoder(12);
    }

    public AuthUser getAuthUser() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();

        return authUserRepository.findById(userPrincipal.getAuthUser().getId()).orElseThrow();
    }

    public String register(RegisterRequest registerRequest) {


    }

    public LoginResponse login(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        System.out.println("deneme");

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(username);
            return LoginResponse.builder()
                    .token(token)
                    .build();
        } else {
            return LoginResponse.builder().build();
        }
    }

    public boolean verifyEmail(String token) {

        AuthUser authUser = authUserRepository.findByVerificationToken(token);

        if (authUser == null)
            return false;

        authUser.getUser().setStatus(UserStatus.APPROVED);

        authUserRepository.save(authUser);

        return true;

    }
}
