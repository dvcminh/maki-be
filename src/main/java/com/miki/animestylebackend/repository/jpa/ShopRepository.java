package com.miki.animestylebackend.repository.jpa;

import com.miki.animestylebackend.model.CuisineType;
import com.miki.animestylebackend.model.Shop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Repository
public interface ShopRepository extends JpaRepository<Shop, UUID> {

    Page<Shop> findDistinctByNameContainsAndRatingBetweenAndIsOpenAndCuisineTypeOrderByRatingDescAllIgnoreCase(String name, BigDecimal ratingStart, BigDecimal ratingEnd, Boolean isOpen, CuisineType cuisineType, Pageable pageable);

    Optional<Shop> findByUser_Id(UUID id);

}
