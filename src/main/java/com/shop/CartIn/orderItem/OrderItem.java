package com.shop.CartIn.orderItem;

import com.shop.CartIn.order.Order;
import com.shop.CartIn.product.Product;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "order_item")
public class OrderItem
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, name = "order_price")
    private int orderPrice; // 단가 * 수량

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    @Builder
    public OrderItem(int quantity, int orderPrice, Order order,Product product) {
        this.quantity = quantity;
        this.orderPrice = orderPrice;
        this.order = order;
        this.product = product;
    }
}
