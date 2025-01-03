package com.miki.animestylebackend.dto.request;

import lombok.Data;

import java.util.UUID;

@Data
public class DriverSaveDtoRequest {
    private UUID id;
    private String vehicleDetails;
}
