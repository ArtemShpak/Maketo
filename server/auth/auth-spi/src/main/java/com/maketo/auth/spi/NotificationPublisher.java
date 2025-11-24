package com.maketo.auth.spi;

import com.maketo.common.messaging.dto.UserDto;
import org.springframework.stereotype.Component;

@Component
public interface NotificationPublisher {
    void publishUserRegistrationEvent(UserDto userDto);
    void publishPasswordResetEvent(UserDto userDto);
}
