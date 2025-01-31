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

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static net.dzakirin.userservice.constant.ErrorCodes.USERNAME_NOT_FOUND;
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
        var tokenGenerated = authServiceClient.generateToken(userDto);

        return RegisterResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getUserRoles().stream()
                        .map(userRole -> userRole.getRole().getRoleName())
                        .toList())
                .accessToken(tokenGenerated.getAccessToken())
                .refreshToken(tokenGenerated.getRefreshToken())
                .build();
    }

    public UserDto findByUsername(String username) {
        // Check if the username contains URL-encoded characters
        String processedUsername = isUrlEncoded(username) ? URLDecoder.decode(username, StandardCharsets.UTF_8) : username;

        var user = userRepository.findByUsername(processedUsername)
                .orElseThrow(() -> new ResourceNotFoundException(USERNAME_NOT_FOUND.getMessage(processedUsername)));

        return UserMapper.toUserDto(user);
    }

    // Helper method to check if a string contains URL-encoded characters
    private boolean isUrlEncoded(String value) {
        // Regex pattern to detect encoded characters like %20, %3A, etc.
        Pattern urlEncodedPattern = Pattern.compile("%[0-9A-Fa-f]{2}");
        return urlEncodedPattern.matcher(value).find();
    }
}
