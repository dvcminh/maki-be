package com.miki.animestylebackend.service;

import com.miki.animestylebackend.model.Driver;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverService {
    List<Driver> getAllDrivers();
    Driver getDriverById(UUID id);
    Driver saveDriver(Driver driver);
    void deleteDriver(UUID id);
}
