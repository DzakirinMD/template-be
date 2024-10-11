package net.dzakirin.userservice.dto.request;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RoleRequestDto {

    private List<String> roles;
}
