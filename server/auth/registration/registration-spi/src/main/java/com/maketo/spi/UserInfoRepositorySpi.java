package com.maketo.spi;

import com.maketo.entities.UserInfo;

public interface UserInfoRepositorySpi {
    boolean userExists(String email);
    void save(UserInfo user);
}
