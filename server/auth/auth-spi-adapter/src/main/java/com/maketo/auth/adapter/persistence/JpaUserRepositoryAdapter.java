package com.maketo.auth.adapter.persistence;

import com.maketo.auth.spi.dto.User;
import com.maketo.auth.spi.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class JpaUserRepositoryAdapter implements UserRepository {

    private final SpringDataUserRepository springDataRepository;

    public JpaUserRepositoryAdapter(SpringDataUserRepository springDataRepository) {
        this.springDataRepository = springDataRepository;
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return springDataRepository.findByEmail(email);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return springDataRepository.findById(id);
    }

    @Override
    public void save(User user) {
        springDataRepository.save(user);
    }

    @Override
    public boolean existsByEmail(String email) {
        return springDataRepository.existsByEmail(email);
    }

}
