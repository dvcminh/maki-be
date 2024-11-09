package com.miki.animestylebackend.dto.response;

import com.miki.animestylebackend.model.CuisineType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ShopData {
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String description;
    private BigDecimal rating;
    private Integer ratingCount;
    private String imageUrl;
    private Boolean isOpen;
    private CuisineType cuisineType;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
}
