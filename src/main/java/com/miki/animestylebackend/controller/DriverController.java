package com.miki.animestylebackend.controller;

import com.miki.animestylebackend.dto.page.PageData;
import com.miki.animestylebackend.dto.request.DriverSaveDtoRequest;
import com.miki.animestylebackend.dto.response.DriverData;
import com.miki.animestylebackend.dto.response.DriverDto;
import com.miki.animestylebackend.mapper.DriverMapper;
import com.miki.animestylebackend.model.Driver;
import com.miki.animestylebackend.service.DriverService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/drivers")
@RequiredArgsConstructor
public class DriverController {
    private final DriverService driverService;
    @GetMapping
    public ResponseEntity<PageData<DriverData>> getAllDrivers(@RequestParam(required = false) String email,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(driverService.getAllDrivers(email, page, size));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DriverDto> getDriverById(@PathVariable UUID id) {
        return ResponseEntity.ok(driverService.getDriverById(id));
    }

    @PostMapping
    public ResponseEntity<DriverDto> createDriver(@RequestBody DriverSaveDtoRequest driver) {
        return ResponseEntity.ok(driverService.saveDriver(driver));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDriver(@PathVariable UUID id) {
        driverService.deleteDriver(id);
        return ResponseEntity.ok().build();
    }
}