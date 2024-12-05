package com.miki.animestylebackend.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ReviewsData {
    private UUID userId;
    private ShopData shopId;
    private DriverData driverId;
    private OrderData orderId;
    private BigDecimal rating;
    private String comment;
}
