package net.dzakirin.cashmanagement.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class CreateAccountDto {
    private String userId;

    @NotNull(message = "Initial balance must not be null")
    @DecimalMin(value = "0.0", message = "Initial balance must be at least 0")
    private BigDecimal initialBalance;
}
