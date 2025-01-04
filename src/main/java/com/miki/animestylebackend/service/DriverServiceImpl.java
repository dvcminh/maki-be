package com.miki.animestylebackend.service;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.DriverSaveDtoRequest;
import com.miki.animestylebackend.dto.request.UpdateLocationRequest;
import com.miki.animestylebackend.dto.response.DriverData;
import com.miki.animestylebackend.dto.response.DriverDto;
import com.miki.animestylebackend.mapper.DriverMapper;
import com.miki.animestylebackend.model.Driver;
import com.miki.animestylebackend.repository.jpa.DriverRepository;
import com.miki.animestylebackend.repository.jpa.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DriverServiceImpl implements DriverService {
    private final DriverMapper driverMapper;
    private final DriverRepository driverRepository;
    private final UserRepository userRepository;
    public PageData<DriverData> getAllDrivers(String email, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        if (email == null) {
            Page<DriverData> driverDataPage = driverRepository.findAll(pageable)
                    .map(driverMapper::toDriverData);
            return new PageData<>(driverDataPage, "Drivers found");
        }
        Page<DriverData> driverDataPage = driverRepository.findByUser_EmailContains(email, pageable)
                .map(driverMapper::toDriverData);
        return new PageData<>(driverDataPage, "Drivers found");
    }

    public DriverDto getDriverById(UUID id) {
        Optional<Driver> driver = driverRepository.findById(id);
        return driverMapper.toDriverDto(driver.orElse(null), "Driver found");
    }

    public DriverDto saveDriver(DriverSaveDtoRequest driver) {
        Driver driver1 = Driver.builder()
                .user(userRepository.findById(driver.getId()).orElse(null))
                .vehicleDetails(driver.getVehicleDetails())
                .verified(false)
                .available(true)
                .build();
        return driverMapper.toDriverDto(driverRepository.save(driver1), "Driver saved");
    }

    public void deleteDriver(UUID id) {
        driverRepository.deleteById(id);
    }

    @Override
    public void updateLocation(UpdateLocationRequest request) {
        Optional<Driver> driver = driverRepository.findById(request.getId());
        if (driver.isPresent()) {
            driver.get().setLatitude(request.getLatitude());
            driver.get().setLongitude(request.getLongitude());
            driverRepository.save(driver.get());
        }
    }
}