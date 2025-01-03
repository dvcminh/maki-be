package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.OrderItemData;
import com.miki.animestylebackend.dto.response.OrderItemDto;
import com.miki.animestylebackend.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class OrderItemMapperImpl implements OrderItemMapper {
    private final ProductMapper productMapper;
    @Override
    public OrderItemDto toOrderItemDto(OrderItem orderItem, String message) {
        if (orderItem == null) {
            return null;
        }
        OrderItemData orderItemData = toOrderItemData(orderItem);

        OrderItemDto orderItemDto = new OrderItemDto();
        orderItemDto.setSuccess(true);
        orderItemDto.setStatus(200);
        orderItemDto.setMessage(message);
        orderItemDto.setData(orderItemData);
        orderItemDto.setTimestamp(LocalDateTime.now());

        return orderItemDto;
    }

    @Override
    public OrderItemData toOrderItemData(OrderItem orderItem) {
        if (orderItem == null) {
            return null;
        }
        OrderItemData orderItemData = new OrderItemData();
        orderItemData.setId(orderItem.getId());
        orderItemData.setProductData(productMapper.toProductData(orderItem.getProduct()));
        orderItemData.setQuantity(orderItem.getQuantity());
        orderItemData.setPricePerUnit(orderItem.getPricePerUnit().multiply(new BigDecimal(orderItem.getQuantity())));
        orderItemData.setSize(orderItem.getSize());
        orderItemData.setColor(orderItem.getColor());

        return orderItemData;
    }

//    @Component
//    public class OrderItemMapper {
//        public OrderItemDto toDTO(OrderItem orderItem) {
//            OrderItemDto dto = new OrderItemDto();
//            dto.setId(orderItem.getId());
//            dto.setProductId(orderItem.getProduct().getId());
//            dto.setProductName(orderItem.getProduct().getName());
//            dto.setQuantity(orderItem.getQuantity());
//            dto.setPricePerUnit(orderItem.getPricePerUnit());
//            return dto;
//        }
//    }

}
