package com.miki.animestylebackend.service;

import com.miki.animestylebackend.model.Driver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverService {
    public List<Driver> getAllDrivers();
    Optional<Driver> getDriverById(UUID id);
    public Driver saveDriver(Driver driver);
    public void deleteDriver(UUID id);
}
