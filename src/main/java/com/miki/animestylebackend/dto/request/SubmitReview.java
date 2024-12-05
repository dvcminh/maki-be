package com.miki.animestylebackend.dto.request;

import jakarta.annotation.Nullable;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SubmitReview {
    @Nullable
    private UUID id;
    private UUID userId;
    @Nullable
    private UUID shopId;
    @Nullable
    private UUID driverId;
    private UUID orderId;
    private BigDecimal rating;
    private String comment;
    private Boolean isShop;
}
