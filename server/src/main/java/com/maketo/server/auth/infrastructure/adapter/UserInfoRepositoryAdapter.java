package com.maketo.server.auth.infrastructure.adapter;

import com.maketo.server.auth.application.port.out.UserInfoRepositoryPort;
import com.maketo.server.auth.domain.user.UserInfo;
import com.maketo.server.auth.infrastructure.persistance.jpa.SpringDataUserInfoJpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserInfoRepositoryAdapter implements UserInfoRepositoryPort {

    private final SpringDataUserInfoJpaRepository userRepository;

    public UserInfoRepositoryAdapter(SpringDataUserInfoJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void save(UserInfo user) {
        userRepository.save(user);
    }

    @Override
    public Optional<UserInfo> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email)));
    }

    @Override
    public Optional<UserInfo> findByActivationToken(String token) {
        return Optional.ofNullable(userRepository.findByActivationToken(token).orElseThrow(() -> new UsernameNotFoundException("User not found with token: " + token)));

    }

    @Override
    public Optional<UserInfo> getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return findByEmail(email);
    }
}
