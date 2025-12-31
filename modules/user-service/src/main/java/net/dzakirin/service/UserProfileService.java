package net.dzakirin.service;

import lombok.RequiredArgsConstructor;
import net.dzakirin.dto.request.UserProfileRequest;
import net.dzakirin.entity.UserProfile;
import net.dzakirin.exception.ResourceNotFoundException;
import net.dzakirin.repository.UserProfileRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;

    public UserProfile getProfile(UUID userId) {
        return userProfileRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found for user ID: " + userId));
    }

    public UserProfile upsertProfile(UUID userId, UserProfileRequest request) {
        UserProfile profile = userProfileRepository.findById(userId)
                .orElse(UserProfile.builder().userId(userId).build());

        profile.setFirstName(request.getFirstName());
        profile.setLastName(request.getLastName());
        profile.setPhoneNumber(request.getPhoneNumber());
        profile.setAddress(request.getAddress());

        return userProfileRepository.save(profile);
    }
}