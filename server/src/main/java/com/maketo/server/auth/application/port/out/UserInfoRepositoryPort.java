package com.maketo.server.auth.application.port.out;

import com.maketo.server.auth.domain.user.UserInfo;

import java.util.Optional;

public interface UserInfoRepositoryPort {
    boolean userExists(String email);
    void save(UserInfo user);
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByActivationToken(String token);
    Optional<UserInfo> getCurrentUser();
}
