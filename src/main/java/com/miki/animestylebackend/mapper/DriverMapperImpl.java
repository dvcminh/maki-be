package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.DriverData;
import com.miki.animestylebackend.dto.response.DriverDto;
import com.miki.animestylebackend.model.Driver;
import com.miki.animestylebackend.model.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DriverMapperImpl implements DriverMapper{
    private final UserMapper userMapper;
    @Override
    public DriverDto toDriverDto(Driver driver, String message) {
        if(driver == null){
            return null;
        }
        DriverData driverData = toDriverData(driver);

        DriverDto driverDto = new DriverDto();
        driverDto.setSuccess(true);
        driverDto.setStatus(200);
        driverDto.setMessage(message);
        driverDto.setData(driverData);
        return driverDto;
    }

    @Override
    public DriverData toDriverData(Driver driver) {
        if(driver == null){
            return null;
        }
        DriverData driverData = new DriverData();
        driverData.setUser(userMapper.toUserData(driver.getUser()));
        driverData.setAvailable(driver.getAvailable());
        driverData.setVerified(driver.getVerified());
        driverData.setVehicleDetail(driver.getVehicleDetails());
        driverData.setLatitude(driver.getLatitude());
        driverData.setLongitude(driver.getLongitude());

        return driverData;
    }
}
