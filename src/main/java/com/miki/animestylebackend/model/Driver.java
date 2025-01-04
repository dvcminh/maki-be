package com.miki.animestylebackend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Driver extends BaseEntity {

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "vehicle_details")
    private String vehicleDetails;
    @Column(name = "available")
    private Boolean available;
    @Column(name = "verified")
    private Boolean verified = false;
    @Column(name = "latitude")
    private Double latitude;
    @Column(name = "longtitude")
    private Double longitude;
}