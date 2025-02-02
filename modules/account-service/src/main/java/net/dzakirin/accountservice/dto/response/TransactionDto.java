package net.dzakirin.accountservice.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import net.dzakirin.accountservice.constant.TransactionType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class TransactionDto {

    private UUID id;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", timezone = "UTC")
    private Instant transactionDate;
    private TransactionType transactionType;
    private BigDecimal amount;
    private AccountDto sourceAccount;
    private AccountDto destinationAccount;
}
