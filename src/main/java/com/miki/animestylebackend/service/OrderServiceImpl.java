package com.miki.animestylebackend.service;


import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.CreateOrderItemRequest;
import com.miki.animestylebackend.dto.request.CreateOrderRequest;
import com.miki.animestylebackend.dto.request.UpdateStatusRequest;
import com.miki.animestylebackend.dto.response.OrderData;
import com.miki.animestylebackend.dto.response.OrderDto;
import com.miki.animestylebackend.exception.NotFoundException;
import com.miki.animestylebackend.exception.OrderNotFoundException;
import com.miki.animestylebackend.exception.VoucherNotFoundException;
import com.miki.animestylebackend.mapper.OrderMapper;
import com.miki.animestylebackend.mapper.UserMapper;
import com.miki.animestylebackend.model.*;
import com.miki.animestylebackend.repository.jpa.OrderItemRepository;
import com.miki.animestylebackend.repository.jpa.OrderRepository;
import com.miki.animestylebackend.repository.jpa.ShopRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService{
    private final ShopRepository shopRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final UserMapper userMapper;
    private final UserService userService;
    private final ShopService shopService;
    private final OrderMapper orderMapper;
    private final VoucherService voucherService;
    private final EmailService emailService;
    private EntityManager entityManager;
    private static final long CACHE_TTL = 60;
    private final RedisTemplate<String, Integer> redisTemplateInteger;
    private static final int MAX_REQUESTS = 1;
    private static final int EXPIRE_TIME = 10;
    // limit access function
    public void limitAccess(String username) {
        ValueOperations<String, Integer> operations = redisTemplateInteger.opsForValue();
        Integer currentCount = operations.get(username);
        if (currentCount == null) {
            operations.set(username, 1, EXPIRE_TIME, TimeUnit.SECONDS);
        } else if (currentCount < MAX_REQUESTS) {
            operations.increment(username);
        } else {
            throw new IllegalStateException("Exceeded maximum requests");
        }
    }
    @Override
    public List<Order> findAllOrdersSortedByDateDescending() {
        String jpql = "SELECT o FROM Order o ORDER BY o.orderDate DESC";
        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class);
        return query.getResultList();
    }

    @Override
    public PageData<OrderData> getALl(User user, int page, int size, String sortBy, Sort.Direction direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
        Page<OrderData> orderDtoPage = orderRepository.findByUser_Id(user.getId(), pageable)
                .map(orderMapper::toOrderData);

        return new PageData<>(orderDtoPage, "Orders found successfully");
    }

    @Override
    public List<Order> viewAll() {
        return orderRepository.findAllByOrderByOrderDateDesc();
    }

    @Override
    public List<Order> findByShippingStatus(String shippingStatus) {
        return orderRepository.findByShippingStatus(shippingStatus);
    }

    @Override
    public List<Order> findByPaymentStatus(String shippingStatus) {
        return orderRepository.findByPaymentStatus(shippingStatus);
    }

    @Override
    @Transactional
    public OrderDto createOrder(User user, CreateOrderRequest createOrderRequest) {
        // limit access
//        limitAccess(createOrderRequest.getEmail());
        int discountPercentage = 0;
        if(!createOrderRequest.getVoucherCode().isEmpty()) {
            Voucher voucher = voucherService.getVoucherByCode(createOrderRequest.getVoucherCode());
            if(voucher == null) {
                throw new VoucherNotFoundException("Voucher not found");
            }
            voucherService.useVoucher(voucher);
            discountPercentage = voucher.getDiscount();
        }
        Shop shop = shopRepository.findById(createOrderRequest.getShopId())
                .orElseThrow(() -> new NotFoundException("Shop with id " + createOrderRequest.getShopId() + " not found"));
        Order order = new Order();
        order.setUser(user);
        order.setShop(shop);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingStatus("PENDING");
        order.setShippingAddress(createOrderRequest.getAddress());
        order.setVoucherCode(createOrderRequest.getVoucherCode());
        order.setUserEmail(createOrderRequest.getEmail());
        order.setPaymentMethod(createOrderRequest.getPaymentMethod());

        if(createOrderRequest.getPaymentMethod().equals(PaymentMethod.VNPAY)) {
            order.setPaymentStatus("PAID");
        } else {
            order.setPaymentStatus("PENDING");
        }

        BigDecimal totalAmount = BigDecimal.ZERO;
        for (CreateOrderItemRequest item : createOrderRequest.getOrderItems()) {
            Product product = productService.getProductById(item.getProductId());
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .order(order)
                    .quantity(item.getQuantity())
                    .pricePerUnit(product.getProductPrice())
                    .size(item.getSize())
                    .color(item.getColor())
                    .build();

            totalAmount = totalAmount.add(product.getProductPrice().multiply(new BigDecimal(item.getQuantity())));
            orderItemRepository.save(orderItem);

            log.info("Adding order item: {}", orderItem);
        }

        totalAmount = totalAmount.subtract(totalAmount.multiply(new BigDecimal(discountPercentage)).divide(new BigDecimal(100)));

        order.setTotalAmount(totalAmount);

        log.info("Order created: {}", order);

        OrderDto orderDto = orderMapper.toOrderDto(orderRepository.save(order), "Order created successfully");

        emailService.sendCustomerOrderDetailLinkEmail(createOrderRequest.getEmail(), "Order created successfully", order.getId().toString());
        emailService.sendAdminOrderDetailLinkEmail("21522348@gm.uit.edu.vn", "Order created successfully", order.getId().toString());

        return orderDto;
    }

    @Override
    public OrderDto findById(UUID id) {
        return orderRepository.findById(id).map(order -> orderMapper.toOrderDto(order, "Order found successfully"))
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + id + " not found"));
    }

    @Override
    public PageData<OrderData> findOrderByUserEmail(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderData> orderDtoPage = orderRepository.findByUserEmailIgnoreCase(email, pageable).map(orderMapper::toOrderData);

        return new PageData<>(orderDtoPage, "Orders found successfully");
    }

    @Override
    public List<Order> getOrdersByUser(String email) {
        return orderRepository.findByUserEmailContaining(email);
    }



    @Override
    public BigDecimal calculateTotalRevenue() {
        return orderRepository.findAll().stream()
                .map(Order::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public List<Order> getOrdersByUserName(String userName) {
        return orderRepository.findByUserEmailContaining(userName);
    }

    @Override
    public void saveOrder(Order order) {
        orderRepository.save(order);
    }





    @Override
    @Transactional
    public OrderDto updateOrderStatus(UUID uuid, UpdateStatusRequest updateStatusRequest) {
        Order order = orderRepository.findById(uuid)
                .orElseThrow(() -> new OrderNotFoundException("Order with id " + uuid + " not found"));

        order.setShippingStatus(updateStatusRequest.getDeliveryStatus());
        order.setPaymentStatus(updateStatusRequest.getPaymentStatus());
//
        emailService.sendEmail(order.getUserEmail(),
                "Order status updated successfully", "Your order status has been updated to "
                + updateStatusRequest.getDeliveryStatus()
                + " and payment status to "
                + updateStatusRequest.getPaymentStatus());
        return orderMapper.toOrderDto(orderRepository.save(order), "Order status updated successfully");
    }

    @Override
    public void deleteOrder(UUID orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public Order getOrderById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException("Order with id " + orderId + " not found"));
    }

    @Override
    public PageData<OrderData> getOrdersByShop(UUID id, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<OrderData> orderDtoPage = orderRepository.findByShop_Id(id, pageable).map(orderMapper::toOrderData);

        return new PageData<>(orderDtoPage, "Orders found successfully");
    }

}
