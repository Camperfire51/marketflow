package com.camperfire.marketflow.service;

import com.camperfire.marketflow.dto.request.LoginRequest;
import com.camperfire.marketflow.dto.request.RegisterRequest;
import com.camperfire.marketflow.dto.response.LoginResponse;
import com.camperfire.marketflow.exception.EmailAlreadyExistsException;
import com.camperfire.marketflow.exception.UsernameAlreadyExistsException;
import com.camperfire.marketflow.model.*;
import com.camperfire.marketflow.model.user.Admin;
import com.camperfire.marketflow.model.user.Customer;
import com.camperfire.marketflow.model.user.Vendor;
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

import java.util.UUID;

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

        return authUserRepository.findById(userPrincipal.getId()).orElseThrow();
    }

    public String register(RegisterRequest registerRequest) {

        if (authUserRepository.existsByEmail(registerRequest.getEmail()))
            throw new EmailAlreadyExistsException("Email already exists");

        if (authUserRepository.existsByUsername(registerRequest.getUsername()))
            throw new UsernameAlreadyExistsException("Username already exists");

        String token = UUID.randomUUID().toString();

        AuthUser authUser = AuthUser.builder()
                .username(registerRequest.getUsername())
                .password(encoder.encode(registerRequest.getPassword()))
                .email(registerRequest.getEmail())
                .isEnabled(true)
                .isAccountNonExpired(true)
                .isAccountNonLocked(true)
                .isCredentialsNonExpired(true)
                .userRole(registerRequest.getUserRole())
                .verificationToken(token)
                .build();

        switch (authUser.getUserRole()) {
            case ROLE_CUSTOMER -> {
                Customer customer = Customer.builder()
                        .authUser(authUser)
                        .address(registerRequest.getAddress())
                        .cart(Cart.builder().build())
                        .status(UserStatus.APPROVED) //TODO: Implement email verification
                        .build();

                customerRepository.save(customer);
            }
            case ROLE_VENDOR -> {
                Vendor vendor = Vendor.builder()
                        .authUser(authUser)
                        .address(registerRequest.getAddress())
                        .status(UserStatus.APPROVED) //TODO: Implement email verification
                        .build();

                vendorRepository.save(vendor);
            }

            case ROLE_ADMIN -> {
                Admin admin = Admin.builder()
                        .authUser(authUser)
                        .address(registerRequest.getAddress())
                        .status(UserStatus.APPROVED)
                        .build();
            }
        }

        authUserRepository.save(authUser);

        String verificationLink = "http://localhost:8080/verify-email?token=" + token;

        //emailVerificationProducer.sendVerificationEmail(registerRequest.getEmail(), verificationLink);
        return verificationLink;
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

        authUser.getBaseUser().setStatus(UserStatus.APPROVED);

        authUserRepository.save(authUser);

        return true;

    }
}
