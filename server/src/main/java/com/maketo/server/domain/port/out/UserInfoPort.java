package com.maketo.server.domain.port.out;

import com.maketo.server.domain.user.model.UserInfo;

import java.util.Optional;

public interface UserInfoPort {
    boolean userExists(String email);
    UserInfo save(UserInfo user);
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByActivationToken(String token);
    Optional<UserInfo> getCurrentUser();
}
