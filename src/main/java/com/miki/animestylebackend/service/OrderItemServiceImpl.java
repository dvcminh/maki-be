package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.response.OrderItemData;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.mapper.OrderItemMapper;
import com.miki.animestylebackend.model.OrderItem;
import com.miki.animestylebackend.model.User;
import com.miki.animestylebackend.repository.jpa.OrderItemRepository;
import com.miki.animestylebackend.repository.jpa.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ShopRepository shopRepository;

    @Override
    public OrderItem createOrderItem(OrderItem orderItem) {
        return orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> findByOrderId(UUID id) {
        return orderItemRepository.findByOrderId(id);
    }

    @Override
    public PageData<OrderItemData> getOrderItemByOrderId(UUID id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderItemData> orderItems = orderItemRepository.findByOrder_Id(id, pageable)
                .map(orderItemMapper::toOrderItemData);

        return new PageData<>(orderItems, "Order Items found");
    }

    @Override
    public List<Map<String, Object>> getTopSellingItems(User user) {
        return orderItemRepository.getTopSellingItemsByShop(shopRepository.findByUser_Id(user.getId()).orElseThrow(() -> new RuntimeException("Shop not found")).getId());
    }
}
