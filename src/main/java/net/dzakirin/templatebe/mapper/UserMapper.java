package net.dzakirin.templatebe.mapper;

import net.dzakirin.templatebe.dto.UserDto;
import net.dzakirin.templatebe.model.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "id", ignore = true) // Assuming you don't want to map the ID for creation
    UserEntity toUserEntity(UserDto dto);

    UserDto toUserDto(UserEntity entity);
}
