package net.dzakirin.userservice.controller;

import lombok.RequiredArgsConstructor;
import net.dzakirin.userservice.dto.UserDto;
import net.dzakirin.userservice.dto.request.RegisterRequestDto;
import net.dzakirin.userservice.dto.response.RegisterResponseDto;
import net.dzakirin.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponseDto> register(
            @RequestBody RegisterRequestDto request
    ) {
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> findByUsername(@PathVariable String username) {
        var userDto = userService.findByUsername(username);
        return ResponseEntity.ok(userDto);
    }
}
