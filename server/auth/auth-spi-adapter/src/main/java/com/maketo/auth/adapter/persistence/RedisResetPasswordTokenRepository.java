package com.maketo.auth.adapter.persistence;

import com.maketo.auth.adapter.persistence.entity.ForgetPasswordToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisResetPasswordTokenRepository extends CrudRepository<ForgetPasswordToken, String> {
}
