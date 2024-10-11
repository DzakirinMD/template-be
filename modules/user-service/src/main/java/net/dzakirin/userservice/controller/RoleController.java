package net.dzakirin.userservice.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.dzakirin.userservice.dto.request.RoleRequestDto;
import net.dzakirin.userservice.dto.response.RoleResponseDto;
import net.dzakirin.userservice.service.RoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping
    public ResponseEntity<RoleResponseDto> getAllRoles(
            @Valid RoleRequestDto roleRequestDto
    ) {
        return ResponseEntity.ok(roleService.getAllRoles(roleRequestDto));
    }
}
