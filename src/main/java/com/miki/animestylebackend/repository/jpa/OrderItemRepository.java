package com.miki.animestylebackend.repository.jpa;

import com.miki.animestylebackend.model.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {

    List<OrderItem> findByOrderId(UUID id);

    Page<OrderItem> findByOrder_Id(UUID id, Pageable pageable);
}

