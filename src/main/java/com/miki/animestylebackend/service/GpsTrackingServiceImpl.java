package com.miki.animestylebackend.service;

import com.miki.animestylebackend.model.GpsTracking;
import com.miki.animestylebackend.repository.jpa.GpsTrackingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GpsTrackingServiceImpl implements GpsTrackingService {

    @Autowired
    private GpsTrackingRepository gpsTrackingRepository;

    public List<GpsTracking> getAllGpsTrackings() {
        return gpsTrackingRepository.findAll();
    }

    public Optional<GpsTracking> getGpsTrackingById(UUID id) {
        return gpsTrackingRepository.findById(id);
    }

    public GpsTracking saveGpsTracking(GpsTracking gpsTracking) {
        return gpsTrackingRepository.save(gpsTracking);
    }

    public void deleteGpsTracking(UUID id) {
        gpsTrackingRepository.deleteById(id);
    }
}