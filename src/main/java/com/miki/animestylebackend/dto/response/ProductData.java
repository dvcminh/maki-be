package com.miki.animestylebackend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;
@Getter
@Setter
public class ProductData {
    private UUID id;
    private String name;
    private String image;
    private CategoryData categoryDto;
    private String description;
    private BigDecimal price;
    private String size;
    private ShopData shopDto;
    private int quantity;
}
