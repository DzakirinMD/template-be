package net.dzakirin.templatebe.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Builder
public class DepartmentDto {
    private UUID id;
    private String name;
    private String description;
    private List<EmployeeDto> employeeDto;
}
