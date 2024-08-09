package net.dzakirin.cashmanagement.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferRequestDto {
    private String sourceAccountNumber;
    private String destinationAccountNumber;
    private BigDecimal amount;
}
