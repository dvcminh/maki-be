package com.miki.animestylebackend.service;

import com.miki.animestylebackend.model.OrderDelivery;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderDeliveryService {
    List<OrderDelivery> getAllOrderDeliveries();
    Optional<OrderDelivery> getOrderDeliveryById(UUID id);
    OrderDelivery saveOrderDelivery(OrderDelivery orderDelivery);
    void deleteOrderDelivery(UUID id);
}
