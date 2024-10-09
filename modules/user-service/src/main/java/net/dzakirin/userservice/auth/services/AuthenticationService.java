package net.dzakirin.userservice.auth.services;

import lombok.RequiredArgsConstructor;
import net.dzakirin.userservice.auth.dto.request.LoginRequest;
import net.dzakirin.userservice.auth.dto.request.RegisterRequest;
import net.dzakirin.userservice.auth.dto.response.LoginResponse;
import net.dzakirin.userservice.auth.dto.response.RegisterResponse;
import net.dzakirin.userservice.auth.exception.NotFoundException;
import net.dzakirin.userservice.model.User;
import net.dzakirin.userservice.model.UserRole;
import net.dzakirin.userservice.repository.RoleRepository;
import net.dzakirin.userservice.repository.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    // Save the user and userRoles in the database
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
    var savedUser = userRepository.save(user);

    // Generate JWT tokens
    var jwtToken = jwtService.generateToken(savedUser);
    var refreshToken = jwtService.generateRefreshToken(savedUser);

    return RegisterResponse.builder()
            .id(savedUser.getId())
            .username(savedUser.getUsername())
            .email(savedUser.getEmail())
            .roles(savedUser.getUserRoles().stream()
                    .map(userRole -> userRole.getRole().getRoleName())
                    .toList())
            .accessToken(jwtToken)
            .refreshToken(refreshToken)
            .build();
  }

  public LoginResponse login(LoginRequest request) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getUsername(),
                    request.getPassword()
            )
    );
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
}
