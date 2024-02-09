package net.dzakirin.templatebe.mapper;

import net.dzakirin.templatebe.dto.EmployeeDto;
import net.dzakirin.templatebe.model.EmployeeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    @Mapping(target = "id", ignore = true)
    EmployeeEntity toEmployeeEntity(EmployeeDto employeeDto);

    EmployeeDto toEmployeeDto(EmployeeEntity employeeEntity);
}
