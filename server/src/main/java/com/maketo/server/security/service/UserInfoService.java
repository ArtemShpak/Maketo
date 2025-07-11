package com.maketo.server.security.service;

import com.maketo.server.security.entity.UserInfo;
import com.maketo.server.security.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    private final UserInfoRepository repository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserInfoService(UserInfoRepository repository, PasswordEncoder encoder) {
        this.repository = repository;
        this.encoder = encoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(username); // Assuming 'email' is used as username
        // Converting UserInfo to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }

    // In UserInfoService.java
    public void addUser(UserInfo userInfo) {
        // Only encode if not already encoded (BCrypt hashes start with $2a$ or $2b$)
        if (!userInfo.getPassword().startsWith("$2a$") && !userInfo.getPassword().startsWith("$2b$")) {
            userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        }
        repository.save(userInfo);
    }

    public UserInfo findByActivationToken(String activationToken) {
        return repository.findByActivationToken(activationToken)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with activation token: " + activationToken));
    }

    public boolean userExists(String email) {
        return repository.findByEmail(email).isPresent();
    }

    public UserInfo getUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }


    public UserInfo findUserByEmail(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
    }

    public UserInfo getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return getUserByEmail(email);
    }

}
