package com.maketo.server.auth.domain.user.factory;

import com.maketo.server.auth.domain.user.UserInfo;

public class UserInfoFactory {
    public UserInfo newInactiveUser(String name, String email, String hashedPassword, String activationToken) {
        UserInfo u = new UserInfo();
        u.setName(name);
        u.setEmail(email);
        u.setPassword(hashedPassword);
        u.setActivationToken(activationToken);
        u.setActive(false);
        u.setRoles("USER");
        return u;
    }
}
