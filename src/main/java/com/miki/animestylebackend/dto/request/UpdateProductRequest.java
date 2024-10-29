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
public class UpdateProductRequest {
    private UUID id;
    private String name;
    private String description;
    private BigDecimal price;
    private int quantity;
    private String image;
    private String category;
}
