package com.example.rudigitalapi.auth.infra.tokens;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.Base64;
import java.util.HexFormat;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.rudigitalapi.security.jwt.JwtProperties;
import com.example.rudigitalapi.shared.exceptions.InvalidCredentialsException;

@Service
public class RefreshTokenService {

    private final RefreshTokenJpaRepository refreshTokenJpaRepository;
    private final JwtProperties jwtProperties;
    private final SecureRandom secureRandom = new SecureRandom();

    public RefreshTokenService(
            RefreshTokenJpaRepository refreshTokenJpaRepository,
            JwtProperties jwtProperties
    ) {
        this.refreshTokenJpaRepository = refreshTokenJpaRepository;
        this.jwtProperties = jwtProperties;
    }

    @Transactional
    public String create(UUID userId) {
        String rawToken = generateRawToken();

        RefreshTokenEntity entity = new RefreshTokenEntity();
        entity.setUserId(userId);
        entity.setTokenHash(hashToken(rawToken));
        entity.setExpiresAt(Instant.now().plusMillis(jwtProperties.getRefreshExpirationMs()));
        refreshTokenJpaRepository.save(entity);

        return rawToken;
    }

    @Transactional
    public UUID validateAndRevoke(String rawToken) {
        RefreshTokenEntity entity = refreshTokenJpaRepository
                .findByTokenHash(hashToken(rawToken))
                .orElseThrow(InvalidCredentialsException::new);

        if (entity.isRevoked() || entity.isExpired()) {
            throw new InvalidCredentialsException();
        }

        entity.setRevokedAt(Instant.now());
        refreshTokenJpaRepository.save(entity);
        return entity.getUserId();
    }

    private String generateRawToken() {
        byte[] bytes = new byte[32];
        secureRandom.nextBytes(bytes);
        return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
    }

    private String hashToken(String rawToken) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashed = digest.digest(rawToken.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(hashed);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("SHA-256 not available", ex);
        }
    }
}
