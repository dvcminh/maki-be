package com.miki.animestylebackend.repository;

import com.miki.animestylebackend.model.OTP;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OTPRepository extends CrudRepository<OTP, String> {
    Optional<OTP> findByEmail(String email);
}
