package com.shop.CartIn.orderItem;

import lombok.Getter;

@Getter
public class OrderItemResponse {
    private Long productId;
    private String productName;
    private int quantity;
    private int orderPrice;

    public OrderItemResponse(OrderItem orderItem) {
        this.productId = orderItem.getId();
        this.productName = orderItem.getProduct().getName();
        this.quantity = orderItem.getQuantity();
        this.orderPrice = orderItem.getOrderPrice();

    }
}
