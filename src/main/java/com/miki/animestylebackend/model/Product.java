package com.miki.animestylebackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "product")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    private String productName;
    @Column(length = 1000)
    private String productDescription;
    private String productSize;
    private BigDecimal productPrice;
    private Integer productQuantity;
    private String productImage;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id")
    private Category category;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shop_id")
    @JsonIgnoreProperties("products")
    private Shop shop;
}
