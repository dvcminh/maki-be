package com.miki.animestylebackend.dto.request;

import lombok.Data;
import java.util.UUID;
@Data
public class OTPValidationRequest {
    private String email;
    private String otp;
}
