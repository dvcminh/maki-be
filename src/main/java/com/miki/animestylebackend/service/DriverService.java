package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.DriverSaveDtoRequest;
import com.miki.animestylebackend.dto.request.UpdateLocationRequest;
import com.miki.animestylebackend.dto.response.DriverData;
import com.miki.animestylebackend.dto.response.DriverDto;

import java.util.UUID;

public interface DriverService {
    PageData<DriverData> getAllDrivers(String name, int page, int size);
    DriverDto getDriverById(UUID id);
    DriverDto saveDriver(DriverSaveDtoRequest driver);
    void deleteDriver(UUID id);

    void updateLocation(UpdateLocationRequest request);
}
