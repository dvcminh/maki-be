package com.miki.animestylebackend.dto.request;

import jakarta.persistence.Column;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class CreateShopRequest {
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String description;
    private String imageUrl;
}
