package com.miki.animestylebackend.model;

import lombok.*;
import lombok.Builder;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@RedisHash("OTP")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OTP {
    @Id
    private String email;
    private String otp;
    private LocalDateTime expirationTime;
}