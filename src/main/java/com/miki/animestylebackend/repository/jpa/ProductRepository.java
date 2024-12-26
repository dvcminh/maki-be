package com.miki.animestylebackend.repository.jpa;

import com.miki.animestylebackend.model.Product;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Page<Product> findByProductNameContainsIgnoreCase(String productName, Pageable pageable);

    Page<Product> findByCategory_NameContainsIgnoreCase(String name, Pageable pageable);

//    Page<Product> findAllById(UUID id, Pageable pageable);

    Page<Product> findByProductNameContainsAndCategory_NameAllIgnoreCase(String productName, String name, Pageable pageable);

    List<Product> findByCategory_Name(String name);

    Page<Product> findByShop_Id(UUID id, Pageable pageable);

    @Query("""
            SELECT p.productName, p.productQuantity, p.productPrice
            FROM Product p
            WHERE p.shop.id = :shopId AND p.productQuantity < :threshold
            ORDER BY p.productQuantity ASC
            """)
    List<Object[]> findLowStockProductsByShopId(@Param("shopId") UUID shopId, @Param("threshold") Integer threshold);

    @Query("""
            SELECT p, SUM(oi.quantity) AS totalSold
            FROM OrderItem oi
            JOIN oi.product p
            WHERE p.shop.id = :shopId
            GROUP BY p.id
            ORDER BY totalSold DESC
            """)
    List<Object[]> findTopSellingProductsByShopId(@Param("shopId") UUID shopId, Pageable pageable);
}
