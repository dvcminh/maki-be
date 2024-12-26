package com.miki.animestylebackend.repository.jpa;

import com.miki.animestylebackend.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.UUID;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    List<OrderItem> findByOrderId(UUID id);

    Page<OrderItem> findByOrder_Id(UUID id, Pageable pageable);

    @Query("SELECT new map(oi.product.productName AS itemName, SUM(oi.quantity) AS totalQuantity) " +
            "FROM OrderItem oi JOIN oi.order o WHERE o.shop.id = :shopId AND o.paymentStatus = 'Completed' " +
            "GROUP BY oi.product.productName ORDER BY totalQuantity DESC")
    List<Map<String, Object>> getTopSellingItemsByShop(UUID shopId);
}

