package net.dzakirin.service;

import lombok.RequiredArgsConstructor;
import net.dzakirin.common.dto.response.BaseResponse;
import net.dzakirin.constant.RoleName;
import net.dzakirin.dto.response.LoginResponse;
import net.dzakirin.entity.Role;
import net.dzakirin.entity.UserCredential;
import net.dzakirin.exception.ResourceNotFoundException;
import net.dzakirin.exception.TokenValidationException; // Import this
import net.dzakirin.mapper.AuthMapper;
import net.dzakirin.repository.RoleRepository;
import net.dzakirin.repository.UserCredentialRepository;
import net.dzakirin.security.JwtUtils;
import net.dzakirin.security.UserDetailsImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException; // Import this
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static net.dzakirin.constant.ErrorCodes.EMAIL_REGISTERED;
import static net.dzakirin.constant.ErrorCodes.INVALID_EMAIL_OR_PASSWORD;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserCredentialRepository userCredentialRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public BaseResponse<LoginResponse> authenticateUser(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtUtils.generateJwtToken(authentication);

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

    public void registerUser(String email, String password, Set<String> strRoles) {
        if (userCredentialRepository.existsByEmail(email)) {
            throw new ResourceNotFoundException(EMAIL_REGISTERED.getMessage());
        }

        UserCredential user = UserCredential.builder()
                .email(email)
                .password(encoder.encode(password))
                .roles(new HashSet<>())
                .build();

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

        userCredentialRepository.save(user);
    }
}