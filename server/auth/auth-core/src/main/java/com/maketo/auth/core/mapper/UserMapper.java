package com.maketo.auth.core.mapper;

import com.maketo.auth.core.domain.User;
import com.maketo.spi.dto.UserDto;

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
    public static UserDto toEmailDto(User user) {
        if (user == null) {
            return null;
        }

        return new UserDto(
                user.getName(),
                user.getEmail(),
                user.getActivationToken()
        );
    }
}

