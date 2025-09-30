package com.maketo.spi;

import com.maketo.entities.UserInfo;

import java.util.Optional;

public interface UserFindSpi {
    Optional<UserInfo> findByEmail(String email);
}
