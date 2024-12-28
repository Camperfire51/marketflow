package com.camperfire.marketflow.service.authUser;

import com.camperfire.marketflow.dto.crud.authUser.AuthUserRequest;
import com.camperfire.marketflow.dto.crud.user.UserRequest;
import com.camperfire.marketflow.dto.logic.login.LoginRequest;
import com.camperfire.marketflow.dto.logic.register.RegisterRequest;
import com.camperfire.marketflow.dto.mapper.AuthUserMapper;
import com.camperfire.marketflow.exception.EmailAlreadyExistsException;
import com.camperfire.marketflow.exception.UsernameAlreadyExistsException;
import com.camperfire.marketflow.exception.WrongCredentialsException;
import com.camperfire.marketflow.model.AuthUser;
import com.camperfire.marketflow.model.UserStatus;
import com.camperfire.marketflow.model.user.User;
import com.camperfire.marketflow.repository.AuthUserRepository;
import com.camperfire.marketflow.service.JWTService;
import com.camperfire.marketflow.service.user.UserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthUserServiceImpl implements AuthUserService{

    private final AuthUserRepository authUserRepository;
    private final AuthUserMapper authUserMapper;

    private final JWTService jwtService;
    private final AuthenticationManager authManager;
    private final BCryptPasswordEncoder encoder;
    private final UserService userService;

    @Autowired
    public AuthUserServiceImpl(AuthUserRepository authUserRepository, AuthUserMapper authUserMapper, JWTService jwtService, AuthenticationManager authManager, UserService userService) {
        this.authUserRepository = authUserRepository;
        this.authUserMapper = authUserMapper;

        this.jwtService = jwtService;
        this.authManager = authManager;
        this.userService = userService;
        encoder = new BCryptPasswordEncoder(12);
    }

    @Transactional
    @Override
    public String register(RegisterRequest request){

        AuthUserRequest authUserRequest = request.getAuthUserRequest();
        UserRequest userRequest = request.getUserRequest();

        authUserRequest.setPassword(encoder.encode(authUserRequest.getPassword()));

        if (authUserRepository.existsByEmail(authUserRequest.getEmail()))
            throw new EmailAlreadyExistsException("Email already exists");

        if (authUserRepository.existsByUsername(authUserRequest.getUsername()))
            throw new UsernameAlreadyExistsException("Username already exists");

        AuthUser authUser = createAuthUser(authUserRequest);

        User user = userService.createUser(userRequest);

        String verificationLink = "http://localhost:8080/verify-email?token=" + authUser.getVerificationToken();

        //emailVerificationProducer.sendVerificationEmail(registerRequest.getEmail(), verificationLink); //TODO: Implement email verification.
        return verificationLink;
    }

    public String login(LoginRequest loginRequest) {

        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        System.out.println("deneme");

        Authentication authentication = authManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(username);
        } else {
            throw new WrongCredentialsException("Wrong credentials");
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

    @Override
    public AuthUser createAuthUser(AuthUserRequest request) {
        String token = UUID.randomUUID().toString();

        AuthUser authUser = authUserMapper.toEntity(request);

        authUser.setEnabled(true);
        authUser.setAccountNonExpired(true);
        authUser.setAccountNonLocked(true);
        authUser.setCredentialsNonExpired(true);
        authUser.setVerificationToken(token);

        return authUserRepository.save(authUser);
    }

    @Override
    public AuthUser readAuthUser(Long id) {
        return null;
    }

    @Override
    public AuthUser updateAuthUser(AuthUserRequest request) {
        AuthUser authUser = authUserRepository.findById(request.getId()).orElseThrow();

        //TODO: Implement update logic.

        return authUserRepository.save(authUser);
    }

    @Override
    public void deleteAuthUser(Long id) {
        AuthUser authUser = authUserRepository.findById(id).orElseThrow();

        authUserRepository.delete(authUser);
    }
}
