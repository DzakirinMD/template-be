package net.dzakirin.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.common.dto.event.OrderEvent;
import net.dzakirin.common.dto.event.OrderProductEvent;
import net.dzakirin.common.security.JwtUtils;
import net.dzakirin.dto.response.OrderProductResponse;
import net.dzakirin.dto.response.OrderResponse;
import net.dzakirin.entity.Order;
import net.dzakirin.entity.OrderItem;

import java.math.BigDecimal;
import java.util.List;

@UtilityClass
public class OrderMapper {

    public static OrderResponse toOrderResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerId(JwtUtils.getCurrentUserId())
                .orderDate(order.getOrderDate())
                .orderProducts(toOrderProductResponseList(order.getOrderItems()))
                .build();
    }

    public static List<OrderResponse> toOrderResponseList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toOrderResponse)
                .toList();
    }

    public static List<OrderProductResponse> toOrderProductResponseList(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderMapper::toOrderProductResponse)
                .toList();
    }

    public static OrderProductResponse toOrderProductResponse(OrderItem orderItem) {
        if (orderItem == null || orderItem.getProduct() == null) {
            return OrderProductResponse.builder()
                    .productId(null)
                    .productTitle("Unknown Product")
                    .quantity(0)
                    .build();
        }

        return OrderProductResponse.builder()
                .productId(orderItem.getProduct().getId())
                .productTitle(orderItem.getProduct().getTitle())
                .quantity(orderItem.getQuantity())
                .build();
    }

    public static OrderEvent toOrderEvent(Order order) {
        return OrderEvent.builder()
                .id(order.getId())
                .customerId(JwtUtils.getCurrentUserId())
                .customerEmail(JwtUtils.getCurrentUserEmail())
                .orderDate(order.getOrderDate())
                .orderProducts(toOrderProductEventList(order.getOrderItems()))
                .build();
    }

    public static List<OrderProductEvent> toOrderProductEventList(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderMapper::toOrderProductEvent)
                .toList();
    }

    public static OrderProductEvent toOrderProductEvent(OrderItem orderItem) {
        if (orderItem == null || orderItem.getProduct() == null) {
            return OrderProductEvent.builder()
                    .productId(null)
                    .productTitle("Unknown Product")
                    .quantity(0)
                    .price(new BigDecimal(0L))
                    .build();
        }

        return OrderProductEvent.builder()
                .productId(orderItem.getProduct().getId())
                .productTitle(orderItem.getProduct().getTitle())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getProduct().getPrice())
                .build();
    }
}
