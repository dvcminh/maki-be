package com.miki.animestylebackend.dto.response;

import lombok.Data;

@Data
public class DriverData {
    private UserData user;
    private String vehicleDetail;
    private Boolean available;
    private Boolean verified;
}
