package com.maketo.auth.spi;

import com.maketo.auth.spi.dto.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository {
    Optional<User> findByEmail(String email);
    Optional<User> findById(UUID id);
    void save(User user);
    boolean existsByEmail(String email);
}
