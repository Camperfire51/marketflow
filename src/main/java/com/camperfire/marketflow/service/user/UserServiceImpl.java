package com.camperfire.marketflow.service.user;

import com.camperfire.marketflow.dto.RegisterRequest;
import com.camperfire.marketflow.dto.crud.user.UserRequest;
import com.camperfire.marketflow.exception.EmailAlreadyExistsException;
import com.camperfire.marketflow.exception.UsernameAlreadyExistsException;
import com.camperfire.marketflow.model.AuthUser;
import com.camperfire.marketflow.model.Cart;
import com.camperfire.marketflow.model.UserStatus;
import com.camperfire.marketflow.model.user.Admin;
import com.camperfire.marketflow.model.user.Customer;
import com.camperfire.marketflow.model.user.User;
import com.camperfire.marketflow.model.user.Vendor;
import com.camperfire.marketflow.repository.user.AdminRepository;
import com.camperfire.marketflow.repository.user.CustomerRepository;
import com.camperfire.marketflow.repository.user.VendorRepository;

import java.util.UUID;

public class UserServiceImpl implements UserService {

    public final AdminRepository adminRepository;
    public final CustomerRepository customerRepository;
    public final VendorRepository vendorRepository;

    public UserServiceImpl(AdminRepository adminRepository, CustomerRepository customerRepository, VendorRepository vendorRepository) {
        this.adminRepository = adminRepository;
        this.customerRepository = customerRepository;
        this.vendorRepository = vendorRepository;
    }

    @Override
    public String register(RegisterRequest request){



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

    @Override
    public User createUser(UserRequest request) {
        return null;
    }

    @Override
    public User readUser(Long id) {
        return null;
    }

    @Override
    public User updateUser(UserUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteUser(Long id) {

    }
}
