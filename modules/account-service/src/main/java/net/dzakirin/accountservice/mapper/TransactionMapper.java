package net.dzakirin.accountservice.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.accountservice.dto.response.AccountDto;
import net.dzakirin.accountservice.dto.response.TransactionDto;
import net.dzakirin.accountservice.model.AccountEntity;
import net.dzakirin.accountservice.model.TransactionEntity;

@UtilityClass
public class TransactionMapper {

    public static TransactionDto toTransactionDto(TransactionEntity transaction, AccountEntity sourceAccount, AccountEntity destinationAccount) {
        return TransactionDto.builder()
                .id(transaction.getId())  // ID from withdrawal transaction
                .transactionDate(transaction.getTransactionDate())
                .transactionType(transaction.getTransactionType())
                .amount(transaction.getAmount())
                .sourceAccount(AccountMapper.toAccountDto(sourceAccount))
                .destinationAccount(AccountMapper.toAccountDto(destinationAccount))
                .build();
    }
}
