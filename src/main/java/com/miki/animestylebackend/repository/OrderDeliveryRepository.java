package com.miki.animestylebackend.repository;

import com.miki.animestylebackend.model.OrderDelivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface OrderDeliveryRepository extends JpaRepository<OrderDelivery, UUID> {
}