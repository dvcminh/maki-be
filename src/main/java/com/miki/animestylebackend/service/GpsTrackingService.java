package com.miki.animestylebackend.service;

import com.miki.animestylebackend.model.GpsTracking;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GpsTrackingService {
    List<GpsTracking> getAllGpsTrackings();
    Optional<GpsTracking> getGpsTrackingById(UUID id);
    GpsTracking saveGpsTracking(GpsTracking gpsTracking);
    void deleteGpsTracking(UUID id);
}
