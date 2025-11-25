package com.maketo.auth.adapter.persistence;

import com.maketo.auth.adapter.persistence.entity.ForgotPasswordToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RedisForgetPasswordTokenRepository extends CrudRepository<ForgotPasswordToken, String> {
}
