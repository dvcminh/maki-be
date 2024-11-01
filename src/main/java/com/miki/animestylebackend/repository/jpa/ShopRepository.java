package com.miki.animestylebackend.repository.jpa;

import com.miki.animestylebackend.model.CuisineType;
import com.miki.animestylebackend.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
@Repository
public interface ShopRepository extends JpaRepository<Shop, UUID> {

    Page<Shop> findDistinctByNameContainsAndRatingBetweenAndIsOpenAndCuisineTypeOrderByRatingDesc(String name, BigDecimal ratingStart, BigDecimal ratingEnd, Boolean isOpen, CuisineType cuisineType, Pageable pageable);
}
