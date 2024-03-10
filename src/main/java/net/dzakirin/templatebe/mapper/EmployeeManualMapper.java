package net.dzakirin.templatebe.mapper;

import net.dzakirin.templatebe.dto.EmployeeDto;
import net.dzakirin.templatebe.model.EmployeeEntity;

public class EmployeeManualMapper {

    private EmployeeManualMapper() {

    }

    public static EmployeeDto toEmployeeDto(EmployeeEntity entity) {
        if (entity == null) {
            return null;
        }

        return EmployeeDto.builder()
                .id(entity.getId())
                .firstName(entity.getFirstName())
                .lastName(entity.getLastName())
                .email(entity.getEmail())
                .address(entity.getAddress()) // Including the address in DTO
                .build();
    }
}
