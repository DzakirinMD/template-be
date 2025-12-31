package net.dzakirin.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.common.dto.request.SignupRequest;
import net.dzakirin.dto.response.UserProfileResponse;
import net.dzakirin.entity.UserProfile;

@UtilityClass
public class UserProfileMapper {

    public static UserProfileResponse toUserProfileResponse(UserProfile userProfile) {
        return UserProfileResponse.builder()
                .firstName(userProfile.getFirstName())
                .lastName(userProfile.getLastName())
                .phoneNumber(userProfile.getPhoneNumber())
                .address(userProfile.getAddress())
                .build();
    }

    public static UserProfile toUserProfile(SignupRequest request) {
        return UserProfile.builder()
                .id(request.getUserId())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .phoneNumber(request.getPhoneNumber())
                .address(request.getAddress())
                .build();
    }
}
