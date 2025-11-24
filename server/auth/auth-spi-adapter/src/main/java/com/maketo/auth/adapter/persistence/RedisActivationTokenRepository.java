package com.maketo.auth.adapter.persistence;

import com.maketo.auth.adapter.persistence.entity.ActivationToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface RedisActivationTokenRepository extends CrudRepository<ActivationToken, String> {
}
