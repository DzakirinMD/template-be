package net.dzakirin.templatebe.dto.response;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositDto {
    private String accountNumber;
    private BigDecimal amount;
}
