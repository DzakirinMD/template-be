package net.dzakirin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dzakirin.common.dto.request.SignupRequest;
import net.dzakirin.common.dto.response.SignupResponse;
import net.dzakirin.dto.request.UserProfileRequest;
import net.dzakirin.entity.UserProfile;
import net.dzakirin.exception.ResourceNotFoundException;
import net.dzakirin.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfile getProfile(UUID userId) {
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user ID: " + userId));
    }

    /**
     * INTERNAL USE: Called during registration via Identity Service.
     * We trust the ID provided in the request because it comes from the trusted upstream service.
     */
    @Transactional
    public SignupResponse createProfileInternal(SignupRequest request) {
        log.info("Creating internal profile for User ID: {}", request.getUserId());

        UserProfile profile = UserProfile.builder()
                .id(request.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();

        UserProfile savedProfile = userProfileRepository.save(profile);

        return SignupResponse.builder()
                .email(request.getEmail())
                .firstName(savedProfile.getFirstName())
                .lastName(savedProfile.getLastName())
                .phoneNumber(savedProfile.getPhoneNumber())
                .address(savedProfile.getAddress())
                .build();
    }

    @Transactional
    public UserProfile upsertProfile(UUID userId, UserProfileRequest request) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElse(UserProfile.builder().id(userId).build());

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setAddress(request.getAddress());

        return userProfileRepository.save(profile);
    }
}