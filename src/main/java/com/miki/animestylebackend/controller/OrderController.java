package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.request.CreateOrderRequest;
import com.miki.animestylebackend.dto.response.OrderData;
import com.miki.animestylebackend.dto.response.OrderDto;
import com.miki.animestylebackend.dto.request.UpdateStatusRequest;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.service.OrderService;
import com.miki.animestylebackend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
public class OrderController extends BaseController{
    private final OrderService orderService;

    @PostMapping("/create_order")
    public ResponseEntity<OrderDto> checkout(@RequestBody CreateOrderRequest createOrderRequest) {
        User user = getCurrentUser();
        return ResponseEntity.ok(orderService.createOrder(user, createOrderRequest));
    }

    @GetMapping("/getOrders/{id}")
    public ResponseEntity<PageData<OrderData>> getOrders(@PathVariable UUID id,
                                                         @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.getOrdersByShop(id, page, size));
    }

    @GetMapping("/getOrderById/{orderId}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID orderId) {
        return ResponseEntity.ok(orderService.findById(orderId));
    }

    @PostMapping("/updateOrderStatus/{orderId}")
    public ResponseEntity<OrderDto> updateOrderStatus(@PathVariable UUID orderId,
                                                      @RequestBody UpdateStatusRequest updateStatusRequest) {
        return ResponseEntity.ok(orderService.updateOrderStatus(orderId, updateStatusRequest));
    }

    @DeleteMapping("/deleteOrder/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable UUID orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("Order deleted successfully");
    }

    @GetMapping("/getOrders")
    public ResponseEntity<PageData<OrderData>> getOrdersByUser(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size,
                                                               @RequestParam(defaultValue = "createdAt") String sortBy,
                                                               @RequestParam(defaultValue = "DESC") Sort.Direction direction) {
        User user = getCurrentUser();
        return ResponseEntity.ok(orderService.getALl(user, page, size, sortBy, direction));
    }

    @GetMapping("/countOrders")
    public ResponseEntity<Integer> countProducts() {
        return ResponseEntity.ok(orderService.viewAll().size());
    }

    @GetMapping("/getOrdersByUserId/{userEmail}")
    public ResponseEntity<PageData<OrderData>> getOrdersByUserId(@PathVariable String userEmail,
                                                                @RequestParam(defaultValue = "0") int page,
                                                                @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.findOrderByUserEmail(userEmail, page, size));
    }
}
