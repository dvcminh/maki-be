package com.miki.animestylebackend.dto.request;

import jakarta.persistence.Enumerated;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class CreateProductRequest {
    private String name;
    private String category;
    private String description;
    private BigDecimal price;
    private UUID shopId;
    private int quantity;
    private String image;
    private String size;
}
