package com.miki.animestylebackend.mapper;

import com.miki.animestylebackend.dto.response.UserData;
import com.miki.animestylebackend.dto.response.UserDto;
import com.miki.animestylebackend.model.User;

public interface UserMapper {

    UserDto toUserDto(User user, String message);
    UserData toUserData(User user);
}