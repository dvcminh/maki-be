package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.OrderItemData;
import com.miki.animestylebackend.dto.response.OrderItemDto;
import com.miki.animestylebackend.model.OrderItem;

public interface OrderItemMapper {
//    OrderItem toOrder(CreateOrderItemRequest createOrderRequest);

    OrderItemDto toOrderItemDto(OrderItem orderItem, String message);
    OrderItemData toOrderItemData(OrderItem orderItem);
}
