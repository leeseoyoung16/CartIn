package com.shop.CartIn.order;

import com.shop.CartIn.orderItem.OrderItem;
import com.shop.CartIn.product.Product;
import com.shop.CartIn.product.ProductRepository;
import com.shop.CartIn.user.User;
import com.shop.CartIn.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService
{
    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    //주문하기
    @Transactional
    public void create(Long userId, List<Long> productIds, List<Integer> quantities) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Order order = new Order();
        order.setUser(user);
        order.setStatus(OrderStatus.ORDERED);

        for(int i=0; i<productIds.size(); i++) {
            Product product = productRepository.findById(productIds.get(i))
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));

            int quantity = quantities.get(i);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setOrderPrice(product.getPrice() * quantity);

            order.addOrderItem(orderItem);
        }
        orderRepository.save(order);
    }

    // 주문 수정 (ORDERED 상태일때만)
    @Transactional
    public void update(Long userId, Long orderId, List<Long> productIds, List<Integer> quantities) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));

        if(!order.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("자신의 주문만 수정할 수 있습니다.");
        }
        if(!order.getStatus().equals(OrderStatus.ORDERED)) {
            throw new IllegalArgumentException("주문중 상태일 때만 주문 수정이 가능합니다.");
        }

        order.getOrderItems().clear();
        order.setTotalPrice(0);

        for(int i=0; i<productIds.size(); i++) {
            Product product = productRepository.findById(productIds.get(i))
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
            int quantity = quantities.get(i);

            OrderItem orderItem = new OrderItem();
            orderItem.setProduct(product);
            orderItem.setQuantity(quantity);
            orderItem.setOrderPrice(product.getPrice() * quantity);

            order.addOrderItem(orderItem);
        }
    }

    //주문 취소하기
    @Transactional
    public void delete(Long userId, Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        if(!order.getUser().getId().equals(userId)) {
            throw new IllegalArgumentException("자신의 주문만 취소할 수 있습니다.");
        }
        if(!order.getStatus().equals(OrderStatus.ORDERED)) {
            throw new IllegalArgumentException("주문중 상태일 때만 취소가 가능합니다.");
        }
        order.setStatus(OrderStatus.CANCELLED);
    }

    //주문 상태 변경 (관리자용)
    @Transactional
    public void change(Long orderId, OrderStatus orderStatus) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다."));
        order.setStatus(orderStatus);
    }

    //사용자별 주문 내역
    @Transactional(readOnly = true)
    public List<Order> getBuyerOrders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));
        return orderRepository.findByUserId(userId);
    }
    //관리자 주문 내역
    @Transactional(readOnly = true)
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
