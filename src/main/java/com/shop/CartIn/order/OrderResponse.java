package com.shop.CartIn.order;

import com.shop.CartIn.orderItem.OrderItemResponse;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public class OrderResponse {
    private Long userId;
    private Long orderId;
    private String status;
    private List<OrderItemResponse> items;
    private int totalPrice;

    public OrderResponse(Order order) {
        this.userId = order.getUser().getId();
        this.orderId = order.getId();
        this.status = order.getStatus().name();
        this.items = order.getOrderItems()
                .stream()
                .map(OrderItemResponse::new)
                .collect(Collectors.toList());
        this.totalPrice = order.getTotalPrice();
    }

}
