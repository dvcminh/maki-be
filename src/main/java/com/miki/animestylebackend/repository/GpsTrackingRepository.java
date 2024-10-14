package com.miki.animestylebackend.repository;

import com.miki.animestylebackend.model.GpsTracking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface GpsTrackingRepository extends JpaRepository<GpsTracking, UUID> {
}