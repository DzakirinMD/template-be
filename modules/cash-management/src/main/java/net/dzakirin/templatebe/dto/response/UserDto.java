package net.dzakirin.templatebe.dto.response;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class UserDto {
    private UUID id;
    private String firstName;
    private String lastName;
    @NotNull
    private String email;
    private String address;
    private List<AccountDto> accountDtoList;
}
