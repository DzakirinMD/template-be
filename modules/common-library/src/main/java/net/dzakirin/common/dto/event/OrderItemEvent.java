package net.dzakirin.common.dto.event;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Builder
public class OrderItemEvent {
    private UUID productId;
    private String productTitle;
    private int quantity;
    private BigDecimal price;
}
