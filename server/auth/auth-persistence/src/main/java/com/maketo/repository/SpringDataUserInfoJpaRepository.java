package com.maketo.repository;

import com.maketo.entities.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SpringDataUserInfoJpaRepository extends JpaRepository<UserInfo, Integer> {
    boolean existsByEmail(String email);
    Optional<UserInfo> findByEmail(String email);
    Optional<UserInfo> findByActivationToken(String token);
}
