package net.dzakirin.accountservice.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.accountservice.dto.response.AccountDto;
import net.dzakirin.accountservice.model.AccountEntity;

@UtilityClass
public class AccountMapper {

    public static AccountDto toAccountDto(AccountEntity accountEntity) {
        return AccountDto.builder()
                .accountNumber(accountEntity.getAccountNumber())
                .balance(accountEntity.getBalance())
                .build();
    }
}
