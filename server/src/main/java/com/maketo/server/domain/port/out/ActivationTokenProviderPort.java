package com.maketo.server.domain.port.out;

public interface ActivationTokenProviderPort {
    String createActivationToken(String email);
    ActivationPayload parseActivationToken(String token);

    record ActivationPayload(Long userId, long expEpochSeconds, String jti) {}
}
