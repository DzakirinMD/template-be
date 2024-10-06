package net.dzakirin.userservice.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class LoginResponse {
    private String token;
    private String type;
    private String refreshToken;
    private UUID id;
    private String username;
    private String email;
    private List<String> roles;
}
