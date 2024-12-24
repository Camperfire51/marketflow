package com.camperfire.marketflow.controller;

import com.camperfire.marketflow.dto.logic.login.LoginRequest;
import com.camperfire.marketflow.dto.request.RegisterRequest;
import com.camperfire.marketflow.dto.response.LoginResponse;
import com.camperfire.marketflow.service.AuthUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    private final AuthUserService authUserService;

    public UserController(AuthUserService authUserService) {
        this.authUserService = authUserService;
    }

    @GetMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authUserService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest registerRequest) {
        String registerResponse = authUserService.register(registerRequest);
        return ResponseEntity.ok(registerResponse); //TODO: Return a register response instead of plain string.
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        if (authUserService.verifyEmail(token))
            return ResponseEntity.ok("Email verified");
        else
            return ResponseEntity.notFound().build();
    }

}
