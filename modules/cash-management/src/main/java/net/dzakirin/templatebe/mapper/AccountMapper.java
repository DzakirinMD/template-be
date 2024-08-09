package net.dzakirin.templatebe.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.templatebe.dto.response.AccountDto;
import net.dzakirin.templatebe.model.AccountEntity;

@UtilityClass
public class AccountMapper {

    public static AccountDto toAccountDto(AccountEntity accountEntity) {
        return AccountDto.builder()
                .accountNumber(accountEntity.getAccountNumber())
                .balance(accountEntity.getBalance())
                .build();
    }
}
