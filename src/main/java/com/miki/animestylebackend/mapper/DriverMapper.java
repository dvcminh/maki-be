package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.DriverData;
import com.miki.animestylebackend.dto.response.DriverDto;
import com.miki.animestylebackend.model.Driver;

public interface DriverMapper {
    DriverDto toDriverDto(Driver order, String message);
    DriverData toDriverData(Driver order);
}
