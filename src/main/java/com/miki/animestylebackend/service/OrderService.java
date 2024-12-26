package com.miki.animestylebackend.service;



import com.miki.animestylebackend.dto.request.CreateOrderRequest;
import com.miki.animestylebackend.dto.response.OrderData;
import com.miki.animestylebackend.dto.response.OrderDto;
import com.miki.animestylebackend.dto.request.UpdateStatusRequest;
import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.model.Order;
import com.miki.animestylebackend.model.User;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface OrderService {
    List<Order> findAllOrdersSortedByDateDescending();
    PageData<OrderData> getALl(User user, int page, int size, String sortBy, Sort.Direction direction);

    List<Order> viewAll();

    List<Order> findByShippingStatus(String shippingStatus);
    List<Order> findByPaymentStatus(String shippingStatus);
    OrderDto createOrder(User user, CreateOrderRequest createOrderRequest);
    OrderDto findById(UUID id);
    PageData<OrderData> findOrderByUserEmail(String email, int page, int size);
    List<Order> getOrdersByUser(String email);

    BigDecimal calculateTotalRevenue();

    List<Order> getOrdersByUserName(String userName);

    void saveOrder(Order order);
    OrderDto updateOrderStatus(UUID uuid, UpdateStatusRequest updateStatusRequest);

    void deleteOrder(UUID orderId);

    Order getOrderById(UUID orderId);

    PageData<OrderData> getOrdersByShop(UUID id, int page, int size);
}
