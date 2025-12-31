package net.dzakirin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.dzakirin.common.dto.request.SignupRequest;
import net.dzakirin.common.dto.response.SignupResponse;
import net.dzakirin.dto.request.UserProfileRequest;
import net.dzakirin.entity.UserProfile;
import net.dzakirin.service.UserProfileService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

    @Value("${application.security.internal-api-key}")
    private String internalApiKey;

    private final UserProfileService userProfileService;
    
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getUserProfile() {
        UUID userId = getUserIdFromAuth();
        return ResponseEntity.ok(userProfileService.getProfile(userId));
    }

    /**
     * INTERNAL ENDPOINT: Called by Auth Service during registration.
     * Secured by a Shared Secret Key.
     */
    @PostMapping("/internal/create")
    public ResponseEntity<SignupResponse> createInternalUser(
            @RequestHeader("X-Internal-Service-Secret") String secretKey,
            @Valid @RequestBody SignupRequest request) { // This DTO must include the UUID

        // 1. Manual Security Check
        if (!internalApiKey.equals(secretKey)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid Internal Key");
        }

        return ResponseEntity.ok(userProfileService.createProfileInternal(request));
    }

    @PutMapping("/profile")
    public ResponseEntity<UserProfile> updateProfile(@Valid @RequestBody UserProfileRequest request) {
        UUID userId = getUserIdFromAuth();
        return ResponseEntity.ok(userProfileService.upsertProfile(userId, request));
    }

    // Helper method to extract User ID from JWT (via Security Context)
    private UUID getUserIdFromAuth() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String principle = auth.getName();
        return UUID.fromString(principle);
    }
}