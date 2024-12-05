package com.miki.animestylebackend.repository.jpa;

import com.miki.animestylebackend.model.DriverReviews;
import com.miki.animestylebackend.model.ShopReviews;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.UUID;

@Repository
public interface DriverReviewsRepository extends JpaRepository<DriverReviews, UUID> {

    Page<DriverReviews> findByDriver_IdAndRatingBetweenOrderByCreatedAtAsc(UUID driver_id, BigDecimal rating, BigDecimal rating2, Pageable pageable);
}