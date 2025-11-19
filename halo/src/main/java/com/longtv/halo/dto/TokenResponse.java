package com.longtv.halo.dto;

import java.time.Instant;

public record TokenResponse (
    String bearer,
    String accessToken,
    String refreshToken,
    Instant issuedAt,
    Instant  accessTokenExpiresAt,
    Instant refreshTokenExpiresAt
) {
    public TokenResponse(String accessToken, String refreshToken, Instant  accessTokenExpiresAt, Instant issuedAt, Instant  refreshTokenExpiresAt) {
        this("Bearer", accessToken, refreshToken, accessTokenExpiresAt, issuedAt, refreshTokenExpiresAt);
    }
}
