package com.maketo.adapter;

import com.maketo.entities.UserInfo;
import com.maketo.repository.SpringDataUserInfoJpaRepository;
import com.maketo.spi.UserFindSpi;
import com.maketo.spi.UserInfoRepositorySpi;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserFindAdapter implements UserFindSpi {

    private final SpringDataUserInfoJpaRepository userRepository;

    public UserFindAdapter(SpringDataUserInfoJpaRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<UserInfo> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
