package com.miki.animestylebackend.repository.jpa;

import com.miki.animestylebackend.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
