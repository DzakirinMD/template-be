package net.dzakirin.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dzakirin.common.dto.request.SignupRequest;
import net.dzakirin.common.dto.response.BaseResponse;
import net.dzakirin.common.dto.response.SignupResponse;
import net.dzakirin.dto.request.UpdateUserProfileRequest;
import net.dzakirin.dto.response.UserProfileResponse;
import net.dzakirin.entity.UserProfile;
import net.dzakirin.exception.ResourceNotFoundException;
import net.dzakirin.mapper.UserProfileMapper;
import net.dzakirin.repository.UserProfileRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public BaseResponse<UserProfileResponse> getProfile(UUID userId) {
        UserProfile userProfile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user ID: " + userId));

        return BaseResponse.<UserProfileResponse>builder()
                .success(true)
                .message("User fetch successfully!")
                .data(UserProfileMapper.toUserProfileResponse(userProfile))
                .build();
    }

    /**
     * INTERNAL USE: Called during registration via Identity Service.
     * We trust the ID provided in the request because it comes from the trusted upstream service.
     */
    @Transactional
    public SignupResponse createProfileInternal(SignupRequest request) {
        log.info("Creating internal profile for User ID: {}", request.getUserId());

        UserProfile profile = UserProfileMapper.toUserProfile(request);

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
    public BaseResponse<UserProfileResponse> updateProfile(UUID userId, UpdateUserProfileRequest request) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user ID: " + userId));

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setAddress(request.getAddress());

        UserProfile savedProfile = userProfileRepository.save(profile);

        return BaseResponse.<UserProfileResponse>builder()
                .success(true)
                .message("User profile updated successfully!")
                .data(UserProfileMapper.toUserProfileResponse(savedProfile))
                .build();
    }
}