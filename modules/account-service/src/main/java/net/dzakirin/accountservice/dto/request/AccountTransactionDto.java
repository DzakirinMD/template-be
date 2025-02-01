package net.dzakirin.accountservice.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountTransactionDto {
    private String accountNumber;
    private BigDecimal amount;
}
