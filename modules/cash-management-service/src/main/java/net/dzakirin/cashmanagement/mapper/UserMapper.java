package net.dzakirin.cashmanagement.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.cashmanagement.dto.response.AccountDto;
import net.dzakirin.cashmanagement.dto.response.UserDto;
import net.dzakirin.cashmanagement.model.AccountEntity;
import net.dzakirin.cashmanagement.model.UserEntity;

@UtilityClass
public class UserMapper {

    public static UserEntity toUserEntity(UserDto dto) {
        if (dto == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());
        entity.setAddress(dto.getAddress());

        return entity;
    }

    public static UserDto toUserDto(UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        return UserDto.builder()
                .id(userEntity.getId())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .email(userEntity.getEmail())
                .address(userEntity.getAddress())
                .accountDtoList(
                        userEntity.getAccounts() != null ?
                                userEntity.getAccounts().stream().map(UserMapper::toAccountDto).toList()
                                : null)
                .build();
    }

    private static AccountDto toAccountDto(AccountEntity accountEntity) {
        return AccountDto.builder()
                .accountNumber(accountEntity.getAccountNumber())
                .balance(accountEntity.getBalance())
                .build();
    }
}
