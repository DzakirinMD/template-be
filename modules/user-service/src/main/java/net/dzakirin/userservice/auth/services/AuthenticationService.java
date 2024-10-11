package net.dzakirin.userservice.auth.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import net.dzakirin.userservice.auth.dto.request.LoginRequest;
import net.dzakirin.userservice.auth.dto.request.RegisterRequest;
import net.dzakirin.userservice.auth.dto.response.LoginResponse;
import net.dzakirin.userservice.auth.dto.response.RegisterResponse;
import net.dzakirin.userservice.auth.exception.InvalidTokenException;
import net.dzakirin.userservice.auth.exception.NotFoundException;
import net.dzakirin.userservice.model.User;
import net.dzakirin.userservice.model.UserRole;
import net.dzakirin.userservice.repository.RoleRepository;
import net.dzakirin.userservice.repository.UserRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final RoleRepository roleRepository;

    @Transactional
    public RegisterResponse register(RegisterRequest request) {
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

        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        tokenService.saveUserToken(user, jwtToken);

        return RegisterResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(user.getUserRoles().stream()
                        .map(userRole -> userRole.getRole().getRoleName())
                        .toList())
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public LoginResponse login(LoginRequest request) {
        var authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);

        var user = userRepository.findByUsername(request.getUsername())
                .orElseThrow();


        var jwtToken = jwtService.generateToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);
        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, jwtToken);

        return LoginResponse.builder()
                .accessToken(jwtToken)
                .refreshToken(refreshToken)
                .build();
    }

    public LoginResponse refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;

        // Check if the Authorization header is present and starts with "Bearer "
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new InvalidTokenException("Invalid token!");
        }

        // Extract the refresh token from the header
        refreshToken = authHeader.substring(7);
        String username = jwtService.extractUsername(refreshToken);

        // Check if the username was successfully extracted from the refresh token
        if (username == null) {
            throw new InvalidTokenException("Invalid refresh token!");
        }

        // Fetch the user associated with the username
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException("User not found!"));

        // Validate the refresh token for the user
        if (!jwtService.isTokenValid(refreshToken, user)) {
            throw new InvalidTokenException("Refresh token is not valid or has expired!");
        }

        // Generate a new access token if the refresh token is valid
        var accessToken = jwtService.generateToken(user);
        tokenService.revokeAllUserTokens(user);
        tokenService.saveUserToken(user, accessToken);

        // Return the new access token and the existing refresh token
        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

}
