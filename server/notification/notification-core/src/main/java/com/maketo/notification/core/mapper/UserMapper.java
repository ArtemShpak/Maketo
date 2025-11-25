package com.maketo.notification.core.mapper;

import com.maketo.common.messaging.dto.UserDto;
import com.maketo.notification.api.dto.UserActivationDto;

public class UserMapper {

    public static UserActivationDto toUserActivationDto(UserDto userDto) {
        return new UserActivationDto(
                userDto.getName(),
                userDto.getEmail(),
                userDto.getActivationToken()
        );
    }
}

