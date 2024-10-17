package com.miki.animestylebackend.dto.response;

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
    private String imageUrl;
    private Boolean isOpen;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
}
