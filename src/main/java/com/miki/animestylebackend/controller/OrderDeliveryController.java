package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.model.OrderDelivery;
import com.miki.animestylebackend.service.OrderDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/orderDeliveries")
public class OrderDeliveryController {

    @Autowired
    private OrderDeliveryService orderDeliveryService;

    @GetMapping
    public ResponseEntity<List<OrderDelivery>> getAllOrderDeliveries() {
        return ResponseEntity.ok(orderDeliveryService.getAllOrderDeliveries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDelivery> getOrderDeliveryById(@PathVariable UUID id) {
        return orderDeliveryService.getOrderDeliveryById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<OrderDelivery> createOrderDelivery(@RequestBody OrderDelivery orderDelivery) {
        return ResponseEntity.ok(orderDeliveryService.saveOrderDelivery(orderDelivery));
    }

    @PutMapping("/{id}")
    public ResponseEntity<OrderDelivery> updateOrderDelivery(@PathVariable UUID id, @RequestBody OrderDelivery orderDelivery) {
        return orderDeliveryService.getOrderDeliveryById(id)
                .map(existingOrderDelivery -> {
                    orderDelivery.setId(existingOrderDelivery.getId());
                    return ResponseEntity.ok(orderDeliveryService.saveOrderDelivery(orderDelivery));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderDelivery(@PathVariable UUID id) {
        orderDeliveryService.deleteOrderDelivery(id);
        return ResponseEntity.ok().build();
    }
}