package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.response.OrderItemData;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.service.OrderItemService;
import com.miki.animestylebackend.service.OrderService;
import com.miki.animestylebackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order_items")
public class OrderItemController extends BaseController {
    private final OrderItemService orderItemService;

    @GetMapping("/{id}")
    public ResponseEntity<PageData<OrderItemData>> getOrderItemByOrderId(@PathVariable UUID id,
                                                                         @RequestParam(defaultValue = "0") int page,
                                                                         @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderItemService.getOrderItemByOrderId(id, page, size));
    }

    @GetMapping("/top-selling-items")
    public ResponseEntity<List<Map<String, Object>>> getTopSellingItems() {
        User user = getCurrentUser();
        return ResponseEntity.ok(orderItemService.getTopSellingItems(user));
    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteOrderItemById(@PathVariable Long id) {
//        orderItemService.deleteOrderItemById(id);
//        return ResponseEntity.ok("OrderItem deleted successfully");
//    }
}
