package net.dzakirin.userservice.service;

import lombok.RequiredArgsConstructor;
import net.dzakirin.userservice.dto.request.RoleRequestDto;
import net.dzakirin.userservice.dto.response.RoleResponseDto;
import net.dzakirin.userservice.mapper.RoleMapper;
import net.dzakirin.userservice.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleResponseDto getAllRoles(RoleRequestDto roleRequestDto) {
        if (roleRequestDto.getRoles().isEmpty()) {
            var listOfRole = roleRepository.findAll();
            return RoleMapper.toRoleResponseDto(listOfRole);
        }

        var listOfRole = roleRepository.findByRoleNameIn(roleRequestDto.getRoles());
        return RoleMapper.toRoleResponseDto(listOfRole);
    }
}
