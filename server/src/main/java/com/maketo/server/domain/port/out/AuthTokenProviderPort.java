package com.maketo.server.domain.port.out;

import java.util.List;

public interface AuthTokenProviderPort {
    String createAuthToken(String email, Long userId, List<String> roles);
    AuthPayload parseAccessToken(String token);

    record AuthPayload(Long userId, String email, List<String> roles, long expEchosSeconds, String jti) {}
}
