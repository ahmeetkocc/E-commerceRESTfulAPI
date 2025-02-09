package com.example.ecommerce.controller;

import com.example.ecommerce.payload.request.LoginRequest;
import com.example.ecommerce.payload.request.SignupRequest;
import com.example.ecommerce.payload.response.JwtResponse;
import com.example.ecommerce.payload.response.MessageResponse;
import com.example.ecommerce.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "User authentication and registration APIs")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(
            summary = "User Login",
            description = "Authenticate user with username and password and return JWT token."
    )
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authService.authenticateUser(loginRequest);
    }

    @PostMapping("/register")
    @Operation(
            summary = "User Registration",
            description = "Register a new user with username, email, password, and roles."
    )
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return authService.registerUser(signupRequest);
    }
}
