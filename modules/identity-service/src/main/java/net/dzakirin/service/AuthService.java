package net.dzakirin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dzakirin.client.UserClient;
import net.dzakirin.common.dto.request.SignupRequest;
import net.dzakirin.common.dto.response.BaseResponse;
import net.dzakirin.common.dto.response.SignupResponse;
import net.dzakirin.constant.RoleName;
import net.dzakirin.dto.response.LoginResponse;
import net.dzakirin.entity.Role;
import net.dzakirin.entity.UserCredential;
import net.dzakirin.exception.ConflictException;
import net.dzakirin.exception.ResourceNotFoundException;
import net.dzakirin.exception.TokenValidationException;
import net.dzakirin.mapper.AuthMapper;
import net.dzakirin.repository.RoleRepository;
import net.dzakirin.repository.UserCredentialRepository;
import net.dzakirin.security.TokenService;
import net.dzakirin.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static net.dzakirin.constant.ErrorCodes.EMAIL_REGISTERED;
import static net.dzakirin.constant.ErrorCodes.INVALID_EMAIL_OR_PASSWORD;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserCredentialRepository userCredentialRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final TokenService tokenService;
    private final UserClient userClient;

    public BaseResponse<LoginResponse> authenticateUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = tokenService.generateJwtToken(authentication);

            UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            Set<String> roles = userDetails.getAuthorities().stream()
                    .map(item -> item.getAuthority())
                    .collect(Collectors.toSet());

            return BaseResponse.<LoginResponse>builder()
                    .success(true)
                    .message("Login Success")
                    .data(AuthMapper.toLoginResponse(jwt, userDetails, roles))
                    .build();

        } catch (AuthenticationException e) {
            throw new TokenValidationException(INVALID_EMAIL_OR_PASSWORD.getMessage());
        }
    }

    @Transactional
    public BaseResponse<SignupResponse> registerUser(SignupRequest signupRequest) {

        if (userCredentialRepository.existsByEmail(signupRequest.getEmail())) {
            throw new ConflictException(EMAIL_REGISTERED.getMessage());
        }

        UserCredential user = UserCredential.builder()
                .email(signupRequest.getEmail())
                .password(encoder.encode(signupRequest.getPassword()))
                .roles(new HashSet<>())
                .build();

        Set<String> strRoles = signupRequest.getRoles();
        if (strRoles == null || strRoles.isEmpty()) {
            Role userRole = roleRepository.findByName(RoleName.CUSTOMER)
                    .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
            user.getRoles().add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role.toLowerCase()) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleName.ADMIN)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        user.getRoles().add(adminRole);
                        break;
                    case "seller":
                        Role sellerRole = roleRepository.findByName(RoleName.SELLER)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        user.getRoles().add(sellerRole);
                        break;
                    case "customer":
                    default:
                        Role userRole = roleRepository.findByName(RoleName.CUSTOMER)
                                .orElseThrow(() -> new ResourceNotFoundException("Error: Role is not found."));
                        user.getRoles().add(userRole);
                }
            });
        }

        UserCredential savedUser = userCredentialRepository.save(user);
        log.info("User Credential saved with ID: {}", savedUser.getId());

        try {
            signupRequest.setUserId(savedUser.getId());
            SignupResponse response = userClient.createUser(signupRequest);

            return BaseResponse.<SignupResponse>builder()
                    .success(true)
                    .message("User registered successfully!")
                    .data(response)
                    .build();
        } catch (Exception e) {
            log.error("Failed to create user profile. Rolling back transaction. Error: {}", e.getMessage());
            throw new RuntimeException("Registration failed: Could not create user profile.");
        }
    }
}