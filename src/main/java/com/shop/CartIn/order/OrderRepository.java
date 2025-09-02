package com.shop.CartIn.order;

import com.shop.CartIn.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long>
{
    List<Order> findByUserId(Long userId);

    List<OrderItem> findByProduct_User_Id(Long sellerId);
}
