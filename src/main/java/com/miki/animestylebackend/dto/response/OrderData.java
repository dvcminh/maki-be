package com.miki.animestylebackend.dto.response;

import com.miki.animestylebackend.model.PaymentMethod;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
public class OrderData {
    private UUID id;
    private String user;
    private UUID shopId;
    private LocalDateTime createdAt;
    private BigDecimal totalAmount;
    private String paymentStatus;
    private String shippingStatus;
    private PaymentMethod paymentMethod;
    private String address;
}
