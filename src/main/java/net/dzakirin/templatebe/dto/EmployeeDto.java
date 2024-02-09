package net.dzakirin.templatebe.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class EmployeeDto {
    private UUID id;
    private String firstName;
    private String lastName;
    @NotNull
    private String email;
}
