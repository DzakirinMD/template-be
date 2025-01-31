package net.dzakirin.userservice.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.userservice.dto.UserDto;
import net.dzakirin.userservice.model.User;

@UtilityClass
public class UserMapper {

    public UserDto toUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .roles(user.getUserRoles().stream()
                        .map(userRole -> userRole.getRole().getRoleName())
                        .toList())
                .permissions(user.getAuthorities().stream()
                        .map(authority -> authority.getAuthority())
                        .filter(auth -> !auth.startsWith("ROLE_")) // Filter permissions
                        .toList())
                .build();
    }
}
