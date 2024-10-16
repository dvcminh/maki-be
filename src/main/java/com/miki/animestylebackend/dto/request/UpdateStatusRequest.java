package com.miki.animestylebackend.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UpdateStatusRequest {
    private String paymentStatus;
    private String deliveryStatus;
}
