package com.miki.animestylebackend.dto.request;

import com.miki.animestylebackend.model.CuisineType;
import lombok.Data;

import java.util.UUID;

@Data
public class ShopSaveDtoRequest {
    private UUID id;
    private String name;
    private String address;
    private String phoneNumber;
    private String email;
    private String description;
    private String imageUrl;
    private CuisineType cuisineType;
}
