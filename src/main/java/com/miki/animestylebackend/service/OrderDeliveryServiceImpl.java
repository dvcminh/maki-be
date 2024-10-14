package com.miki.animestylebackend.service;

import com.miki.animestylebackend.model.OrderDelivery;
import com.miki.animestylebackend.repository.OrderDeliveryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderDeliveryServiceImpl implements OrderDeliveryService {

    @Autowired
    private OrderDeliveryRepository orderDeliveryRepository;

    public List<OrderDelivery> getAllOrderDeliveries() {
        return orderDeliveryRepository.findAll();
    }

    public Optional<OrderDelivery> getOrderDeliveryById(UUID id) {
        return orderDeliveryRepository.findById(id);
    }

    public OrderDelivery saveOrderDelivery(OrderDelivery orderDelivery) {
        return orderDeliveryRepository.save(orderDelivery);
    }

    public void deleteOrderDelivery(UUID id) {
        orderDeliveryRepository.deleteById(id);
    }
}