package com.maketo.auth.spi.adapter;
import com.maketo.auth.spi.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserInfoJpaRepository extends JpaRepository<User, UUID> {
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
    Optional<User> findByActivationToken(String token);
}
