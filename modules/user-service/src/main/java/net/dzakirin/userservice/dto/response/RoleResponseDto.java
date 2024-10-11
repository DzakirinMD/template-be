package net.dzakirin.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoleResponseDto {

    private List<String> roles;
}
