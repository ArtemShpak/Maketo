package com.maketo.auth.core.mapper;

import com.maketo.auth.spi.dto.User;
import com.maketo.common.messaging.dto.UserDto;

/**
 * Маппер для преобразования доменной модели User в UserDto для email-сервиса
 */
public class UserMapper {

    private UserMapper() {
        // Utility class
    }

    /**
     * Преобразует User в UserDto для отправки email уведомлений
     * @param user доменная модель пользователя
     * @return DTO с данными для email-сервиса
     */
    public static UserDto toEmailDto(User user, String token) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getName(),
                user.getEmail(),
                token
        );
    }
}

