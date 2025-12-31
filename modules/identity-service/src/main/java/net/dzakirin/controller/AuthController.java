package net.dzakirin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.dzakirin.common.dto.response.BaseResponse;
import net.dzakirin.dto.request.LoginRequest;
import net.dzakirin.dto.request.SignupRequest;
import net.dzakirin.dto.response.LoginResponse;
import net.dzakirin.security.UserDetailsImpl;
import net.dzakirin.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<LoginResponse>> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.authenticateUser(loginRequest.getEmail(), loginRequest.getPassword()));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        
        authService.registerUser(
            signUpRequest.getEmail(), 
            signUpRequest.getPassword(), 
            signUpRequest.getRoles()
        );

        return ResponseEntity.ok().body("User registered successfully!");
    }
}