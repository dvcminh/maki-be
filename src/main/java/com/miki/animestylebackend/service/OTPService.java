package com.miki.animestylebackend.service;

import com.miki.animestylebackend.model.OTP;
import com.miki.animestylebackend.repository.OTPRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class OTPService {
    private static final int OTP_LENGTH = 6;
    private static final int OTP_EXPIRATION_MINUTES = 5;
    @Autowired
    private final OTPRepository otpRepository;
    private final EmailService emailService;
    public String generateOTP(String email) {
        String otp = generateRandomOTP();
        LocalDateTime expirationTime = LocalDateTime.now().plusMinutes(OTP_EXPIRATION_MINUTES);
        OTP otpEntity = new OTP(email, otp, expirationTime);
        otpRepository.save(otpEntity);
        emailService.sendOTPEmail(email.trim(), otp);
        return otp;
    }
    public boolean validateOTP(String email, String otp) {
        if (email == null || otp == null || email.isEmpty() || otp.isEmpty()) {
            throw new IllegalArgumentException("Email and OTP must not be null or empty");
        }
        Optional<OTP> otpEntity = otpRepository.findByEmail(email);
        if (otpEntity.isEmpty()) {
            throw new IllegalArgumentException("OTP not found");
        }
        OTP storedOTP = otpEntity.get();
        if (LocalDateTime.now().isAfter(storedOTP.getExpirationTime())) {
            otpRepository.delete(storedOTP);
            throw new IllegalArgumentException("OTP expired");
        }
        if (!storedOTP.getOtp().equals(otp)) {
            throw new IllegalArgumentException("Invalid OTP");
        }
        otpRepository.delete(storedOTP);
        return true;
    }
    private String generateRandomOTP() {
        return new SecureRandom()
                .ints(OTP_LENGTH, 0, 10)
                .mapToObj(Integer::toString)
                .collect(Collectors.joining());
    }
}

