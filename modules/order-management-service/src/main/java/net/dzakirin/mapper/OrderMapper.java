package net.dzakirin.mapper;

import lombok.experimental.UtilityClass;
import net.dzakirin.common.dto.event.OrderEvent;
import net.dzakirin.common.dto.event.OrderItemEvent;
import net.dzakirin.common.security.JwtUtils;
import net.dzakirin.dto.response.OrderItemsResponse;
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
                .orderItems(toOrderProductResponseList(order.getOrderItems()))
                .build();
    }

    public static List<OrderResponse> toOrderResponseList(List<Order> orders) {
        return orders.stream()
                .map(OrderMapper::toOrderResponse)
                .toList();
    }

    public static List<OrderItemsResponse> toOrderProductResponseList(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderMapper::toOrderProductResponse)
                .toList();
    }

    public static OrderItemsResponse toOrderProductResponse(OrderItem orderItem) {
        if (orderItem == null || orderItem.getProduct() == null) {
            return OrderItemsResponse.builder()
                    .productId(null)
                    .productTitle("Unknown Product")
                    .quantity(0)
                    .build();
        }

        return OrderItemsResponse.builder()
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
                .orderItems(toOrderProductEventList(order.getOrderItems()))
                .build();
    }

    public static List<OrderItemEvent> toOrderProductEventList(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(OrderMapper::toOrderProductEvent)
                .toList();
    }

    public static OrderItemEvent toOrderProductEvent(OrderItem orderItem) {
        if (orderItem == null || orderItem.getProduct() == null) {
            return OrderItemEvent.builder()
                    .productId(null)
                    .productTitle("Unknown Product")
                    .quantity(0)
                    .price(new BigDecimal(0L))
                    .build();
        }

        return OrderItemEvent.builder()
                .productId(orderItem.getProduct().getId())
                .productTitle(orderItem.getProduct().getTitle())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getProduct().getPrice())
                .build();
    }
}
