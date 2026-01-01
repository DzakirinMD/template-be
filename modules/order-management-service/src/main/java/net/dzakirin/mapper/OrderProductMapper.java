package net.dzakirin.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.dto.request.OrderProductRequest;
import net.dzakirin.dto.request.OrderRequest;
import net.dzakirin.entity.Order;
import net.dzakirin.entity.OrderItem;
import net.dzakirin.entity.Product;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@UtilityClass
public class OrderProductMapper {

    public static List<OrderItem> toOrderProductList(OrderRequest orderRequest, Order order, Map<UUID, Product> productMap) {
        return orderRequest.getOrderProducts().stream()
                .map(request -> toOrderProduct(request, order, productMap))
                .toList();
    }

    private static OrderItem toOrderProduct(OrderProductRequest request, Order order, Map<UUID, Product> productMap) {
        Product product = productMap.get(request.getProductId());
        return OrderItem.builder()
                .product(product)
                .quantity(request.getQuantity())
                .order(order)
                .build();
    }
}
