package net.dzakirin.userservice.service;


import lombok.RequiredArgsConstructor;
import net.dzakirin.userservice.dto.UserDto;
import net.dzakirin.userservice.dto.request.RegisterRequestDto;
import net.dzakirin.userservice.dto.response.GenerateTokenResponseDto;
import net.dzakirin.userservice.dto.response.RegisterResponseDto;
import net.dzakirin.userservice.exception.NotFoundException;
import net.dzakirin.userservice.exception.ResourceNotFoundException;
import net.dzakirin.userservice.mapper.UserMapper;
import net.dzakirin.userservice.model.User;
import net.dzakirin.userservice.model.UserRole;
import net.dzakirin.userservice.repository.RoleRepository;
import net.dzakirin.userservice.repository.UserRepository;
import net.dzakirin.userservice.webclient.AuthServiceClient;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.stream.Collectors;

import static net.dzakirin.userservice.constant.ErrorCodes.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthServiceClient authServiceClient;

    @Transactional
    public RegisterResponseDto register(RegisterRequestDto request) {
        var listOfRoles = roleRepository.findByRoleNameIn(request.getRoles());
        if (listOfRoles.isEmpty() || listOfRoles.size() != request.getRoles().size()) {
            throw new NotFoundException("One or more roles not found");
        }

        // Save the user and userRoles
        var user = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();
        var userRoles = listOfRoles.stream()
                .map(role -> UserRole.builder()
                        .user(user)
                        .role(role)
                        .build())
                .collect(Collectors.toSet());
        user.setUserRoles(userRoles);
        userRepository.save(user);

        // Call external service to generate JWT token using WebClient
        var userDto = UserMapper.toUserDto(user);
        Mono<GenerateTokenResponseDto> loginResponseMono = authServiceClient.generateToken(userDto);
        GenerateTokenResponseDto loginResponse = loginResponseMono.block(); // Blocking only for simplicity; in production, handle it reactively.

        return RegisterResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getUserRoles().stream()
                        .map(userRole -> userRole.getRole().getRoleName())
                        .toList())
                .accessToken(loginResponse.getAccessToken())
                .refreshToken(loginResponse.getRefreshToken())
                .build();
    }

    public UserDto findById(UUID userId) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException(USER_NOT_FOUND.getMessage(String.valueOf(userId))));
        return UserMapper.toUserDto(user);
    }
}
