package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.response.OrderItemData;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.OrderItem;
import com.miki.animestylebackend.model.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderItemService {
    OrderItem createOrderItem(OrderItem orderItem);
    List<OrderItem> findByOrderId(UUID id);

    PageData<OrderItemData> getOrderItemByOrderId(UUID id, int page, int size);
    List<Map<String, Object>> getTopSellingItems(User user);

}
