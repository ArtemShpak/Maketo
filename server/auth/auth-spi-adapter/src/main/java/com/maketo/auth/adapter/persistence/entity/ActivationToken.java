package com.maketo.auth.adapter.persistence.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;
import java.util.UUID;

@RedisHash(value = "activation_tokens", timeToLive = 860)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivationToken {

    @Id
    private String token;

    private UUID userId;

    private LocalDateTime createdAt;

    public ActivationToken(String token, UUID userId) {
        this.token = token;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }
}
