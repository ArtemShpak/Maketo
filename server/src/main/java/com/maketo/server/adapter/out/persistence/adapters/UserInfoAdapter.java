package com.maketo.server.adapter.out.persistence.adapters;

import com.maketo.server.adapter.out.persistence.jpa.UserInfoRepository;
import com.maketo.server.domain.port.out.UserInfoPort;
import com.maketo.server.domain.user.model.UserInfo;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public class UserInfoAdapter implements UserInfoPort {

    private final UserInfoRepository userRepository;

    public UserInfoAdapter(UserInfoRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean userExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public UserInfo save(UserInfo user) {
        return userRepository.save(user);
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
