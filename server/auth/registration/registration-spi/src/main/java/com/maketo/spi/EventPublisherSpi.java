package com.maketo.spi;

import com.maketo.events.UserRegisteredEvent;

public interface EventPublisherSpi {
    void publishUserRegisteredEvent(UserRegisteredEvent event);
}
