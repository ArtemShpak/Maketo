package com.maketo.adapter;

import com.maketo.entities.UserInfo;
import com.maketo.repository.SpringDataUserInfoJpaRepository;
import com.maketo.spi.UserInfoRepositorySpi;
import org.springframework.stereotype.Component;

@Component
public class UserInfoRepositoryAdapter implements UserInfoRepositorySpi {

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

//    @Override
//    public Optional<UserInfo> findByEmail(String email) {
//        return userRepository.findByEmail(email);
//    }
//
//    @Override
//    public Optional<UserInfo> findByActivationToken(String token) {
//        return userRepository.findByActivationToken(token);
//    }
}
