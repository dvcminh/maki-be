package com.miki.animestylebackend.repository.jpa;


import com.miki.animestylebackend.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Range;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {

    List<Order> findByUserEmailContaining(String email);

//    List<Order> findByUser(User user);
    Optional<Order> findById(UUID id);
//    Page<Order> findByUserId(UUID id, Pageable pageable);
    List<Order> findByShippingStatus(String shippingStatus);
    List<Order> findByPaymentStatus(String paymentStatus);
    List<Order> findAllByOrderByOrderDateDesc();

    Page<Order> findByUserEmailIgnoreCase(String userEmail, Pageable pageable);

    Page<Order> findByShop_Id(UUID id, Pageable pageable);
}

