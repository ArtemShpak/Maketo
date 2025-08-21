package com.maketo.server.auth.infrastructure.security;

import com.maketo.server.auth.application.port.out.UserInfoRepositoryPort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserInfoDetailsService implements UserDetailsService {
    private final UserInfoRepositoryPort repo;

    public UserInfoDetailsService(UserInfoRepositoryPort loadUserByEmailPort) {
        this.repo = loadUserByEmailPort;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repo.findByEmail(username)
                .map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
