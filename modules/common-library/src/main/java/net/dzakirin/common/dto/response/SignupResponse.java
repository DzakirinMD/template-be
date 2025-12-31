package net.dzakirin.common.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SignupResponse{
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String address;
}

