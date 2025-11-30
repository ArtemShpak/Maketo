package com.maketo.auth.security;

import com.maketo.auth.spi.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UUID id = UUID.fromString(username);
            return userRepository.findById(id)
                    .map(UserDetailsImpl::new)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found by id: " + username));
        } catch (IllegalArgumentException ignored) {
            return userRepository.findByEmail(username)
                    .map(UserDetailsImpl::new)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found by email: " + username));
        }
    }
}
