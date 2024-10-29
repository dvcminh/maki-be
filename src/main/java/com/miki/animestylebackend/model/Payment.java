package com.miki.animestylebackend.model;

import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToOne;
import lombok.*;
import lombok.Builder;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment extends BaseEntity{
    @Enumerated
    private PaymentMethod paymentMethod;
    @Enumerated
    private PaymentStatus paymentStatus;
    private String paymentAmount;
    @OneToOne
    private Order order;
}
