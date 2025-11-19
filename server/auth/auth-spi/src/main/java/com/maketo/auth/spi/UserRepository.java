package com.maketo.auth.spi;

import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface UserRepository<T> {
    Optional<T> findByEmail(String email);
    T save(T user);
    boolean existsByEmail(String email);
}
