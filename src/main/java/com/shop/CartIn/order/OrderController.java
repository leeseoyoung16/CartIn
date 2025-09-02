package com.shop.CartIn.order;

import com.shop.CartIn.config.CustomUserDetails;
import com.shop.CartIn.orderItem.OrderItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController
{
    private final OrderService orderService;

    //주문하기
    @PostMapping
    public ResponseEntity<String> order(@AuthenticationPrincipal CustomUserDetails userDetails,
                                      @RequestBody OrderRequest orderRequest) {
        orderService.create(userDetails.getId(), orderRequest.getProductIds(), orderRequest.getQuantities());
        return ResponseEntity.ok("주문이 성공적으로 완료되었습니다.");
    }

    // 주문 수정 (ORDERED 상태일때만)
    @PutMapping("/{orderId}")
    public ResponseEntity<String> updateOrder(@AuthenticationPrincipal CustomUserDetails userDetails,
                                              @PathVariable Long orderId, @RequestBody OrderRequest orderRequest) {
        orderService.update(userDetails.getId(), orderId, orderRequest.getProductIds(), orderRequest.getQuantities());
        return ResponseEntity.ok("주문이 성공적으로 수정되었습니다.");
    }

    //주문 취소하기
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId,
                                              @AuthenticationPrincipal CustomUserDetails userDetails) {
        orderService.delete(userDetails.getId(), orderId);
        return ResponseEntity.ok("주문이 성공적으로 취소되었습니다.");
    }
    //주문 상태 변경 (판매자용)
    @PatchMapping("/{orderId}/status")
    public ResponseEntity<String> changeStatus(@AuthenticationPrincipal CustomUserDetails userDetails,
                                               @PathVariable Long orderId,
                                               @RequestParam OrderStatus status) {
        orderService.change(userDetails.getId(), orderId, status);
        String orderName = status.name();
        return ResponseEntity.ok("상품 상태가 " + orderName + "로 변경되었습니다.");
    }

    //사용자별 주문 내역
    @GetMapping("/me")
    public ResponseEntity<List<OrderResponse>> getMyOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<OrderResponse> response = orderService.getByUserOrders(userDetails.getId()).stream()
                .map(OrderResponse::new)
                .toList();
        return ResponseEntity.ok(response);
    }
    //판매자별 주문 내역
    @GetMapping("/seller/me")
    public ResponseEntity<List<OrderItemResponse>> getSellerOrders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<OrderItemResponse> responses = orderService.getBySeller(userDetails.getId()).stream()
                .map(OrderItemResponse::new)
                .toList();
        return ResponseEntity.ok(responses);
    }

}
