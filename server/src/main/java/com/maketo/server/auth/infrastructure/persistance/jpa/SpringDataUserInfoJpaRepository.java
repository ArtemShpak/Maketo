package com.maketo.server.auth.infrastructure.persistance.jpa;

import com.maketo.server.auth.domain.user.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserInfoJpaRepository extends JpaRepository<UserInfo, Integer> {
    boolean existsByEmail(String email);
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByActivationToken(String token);
}
