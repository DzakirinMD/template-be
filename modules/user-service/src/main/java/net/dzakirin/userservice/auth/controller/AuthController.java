package net.dzakirin.userservice.auth.controller;

import lombok.RequiredArgsConstructor;
import net.dzakirin.userservice.auth.dto.request.LoginRequest;
import net.dzakirin.userservice.auth.dto.request.RegisterRequest;
import net.dzakirin.userservice.auth.dto.response.LoginResponse;
import net.dzakirin.userservice.auth.dto.response.RegisterResponse;
import net.dzakirin.userservice.auth.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
            @RequestBody RegisterRequest request
    ) {
        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }
}
