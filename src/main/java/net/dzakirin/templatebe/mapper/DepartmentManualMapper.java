package net.dzakirin.templatebe.mapper;

import net.dzakirin.templatebe.dto.DepartmentDto;
import net.dzakirin.templatebe.model.DepartmentEntity;

public class DepartmentManualMapper {

    private DepartmentManualMapper() {

    }

    public static DepartmentDto toDepartmentDto(DepartmentEntity entity) {
        if (entity == null) {
            return null;
        }

        return DepartmentDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .employeeDto(entity.getEmployees().stream()
                        .map(EmployeeManualMapper::toEmployeeDto)
                        .toList())
                .build();
    }
}
