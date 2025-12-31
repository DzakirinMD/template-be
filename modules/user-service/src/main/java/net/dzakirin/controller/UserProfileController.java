package net.dzakirin.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.dzakirin.dto.request.UserProfileRequest;
import net.dzakirin.entity.UserProfile;
import net.dzakirin.service.UserProfileService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService userProfileService;
    
    @GetMapping("/profile")
    public ResponseEntity<UserProfile> getUserProfile() {
        UUID userId = getUserIdFromAuth();
        return ResponseEntity.ok(userProfileService.getProfile(userId));
    }

    @PostMapping("/profile")
    public ResponseEntity<UserProfile> upsertProfile(@Valid @RequestBody UserProfileRequest request) {
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