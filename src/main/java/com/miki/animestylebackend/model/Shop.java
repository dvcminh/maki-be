package com.miki.animestylebackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "shops")
public class Shop extends BaseEntity {
    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "description")
    private String description;

    @Column(name = "rating")
    private BigDecimal rating;

    @Column(name = "rating_count")
    private Integer ratingCount;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "is_open")
    private Boolean isOpen = false;

    @Column(name = "verified")
    private Boolean verified = false;

    @Column(name = "is_deleted")
    private Boolean isDeleted = false;

    @Column(name = "open_time")
    private LocalDateTime openTime = LocalDateTime.now();

    @Column(name = "close_time")
    private LocalDateTime closeTime = LocalDateTime.now();

    @Column(name = "cuisine_type")
    @Enumerated(value = EnumType.STRING)
    private CuisineType cuisineType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}