package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.OrderDto;
import com.miki.animestylebackend.dto.response.OrderData;
import com.miki.animestylebackend.model.Order;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toOrderDto(Order order, String message) {
        if (order == null) {
            return null;
        }

        OrderData orderData = toOrderData(order);

        OrderDto orderDto = new OrderDto();
        orderDto.setSuccess(true);
        orderDto.setStatus(200);
        orderDto.setMessage(message);
        orderDto.setData(orderData);
        orderDto.setTimestamp(LocalDateTime.now());

        return orderDto;
    }

    @Override
    public OrderData toOrderData(Order order) {
        if (order == null) {
            return null;
        }

        OrderData orderData = new OrderData();
        orderData.setId(order.getId());
        orderData.setShopId(order.getShop().getId());
        orderData.setUser(order.getUserEmail());
        orderData.setCreatedAt(order.getOrderDate());
        orderData.setTotalAmount(order.getTotalAmount());
        orderData.setPaymentStatus(order.getPaymentStatus());
        orderData.setShippingStatus(order.getShippingStatus());
        orderData.setPaymentMethod(order.getPaymentMethod());
        orderData.setAddress(order.getShippingAddress());

        return orderData;
    }
}