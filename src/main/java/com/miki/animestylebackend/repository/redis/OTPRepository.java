package com.miki.animestylebackend.repository.redis;

import com.miki.animestylebackend.model.OTP;
import org.springframework.data.keyvalue.repository.KeyValueRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends KeyValueRepository<OTP, String> {
    Optional<OTP> findByEmail(String email);
}
