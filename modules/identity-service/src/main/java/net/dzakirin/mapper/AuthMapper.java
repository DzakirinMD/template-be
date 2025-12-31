package net.dzakirin.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.dto.response.LoginResponse;
import net.dzakirin.security.UserDetailsImpl;

import java.util.Set;

@UtilityClass
public class AuthMapper {

    public static LoginResponse toLoginResponse(String jwt, UserDetailsImpl userDetails, Set<String> roles) {
        return LoginResponse.builder()
                .token(jwt)
                .type("Bearer")
                .email(userDetails.getUsername())
                .id(userDetails.getId().toString())
                .roles(roles)
                .build();
    }
}
