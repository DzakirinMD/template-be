package net.dzakirin.authservice.controller;

import lombok.RequiredArgsConstructor;
import net.dzakirin.authservice.dto.UserDto;
import net.dzakirin.authservice.dto.request.LoginRequest;
import net.dzakirin.authservice.dto.response.LoginResponse;
import net.dzakirin.authservice.services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationService authenticationService;



//    @PostMapping("/register")
//    public ResponseEntity<RegisterResponse> register(
//            @RequestBody RegisterRequest request
//    ) {
//        return ResponseEntity.ok(authenticationService.register(request));
//    }
//
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }


    @PostMapping("/generate-token")
    public ResponseEntity<LoginResponse> generateToken(@RequestBody UserDto userDto) {
        return ResponseEntity.ok(authenticationService.generateToken(userDto));
    }

//    @PostMapping("/refresh-token")
//    public ResponseEntity<LoginResponse> refreshToken(HttpServletRequest request, HttpServletResponse response) {
//        return ResponseEntity.ok(authenticationService.refreshToken(request, response));
//    }
}
