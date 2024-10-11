package net.dzakirin.userservice.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.userservice.dto.response.RoleResponseDto;
import net.dzakirin.userservice.model.Role;

import java.util.List;

@UtilityClass
public class RoleMapper {

    public RoleResponseDto toRoleResponseDto(List<Role> roles) {
        return RoleResponseDto.builder()
                .roles(roles.stream()
                        .map(Role::getRoleName)
                        .toList())
                .build();
    }
}
