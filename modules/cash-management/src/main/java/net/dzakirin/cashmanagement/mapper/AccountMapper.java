package net.dzakirin.cashmanagement.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.cashmanagement.dto.response.AccountDto;
import net.dzakirin.cashmanagement.model.AccountEntity;

@UtilityClass
public class AccountMapper {

    public static AccountDto toAccountDto(AccountEntity accountEntity) {
        return AccountDto.builder()
                .accountNumber(accountEntity.getAccountNumber())
                .balance(accountEntity.getBalance())
                .build();
    }
}
