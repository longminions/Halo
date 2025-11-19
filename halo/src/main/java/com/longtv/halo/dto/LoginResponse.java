package com.longtv.halo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

public record LoginResponse (
    Long userId,
    String username,
    String accessToken,
    String refreshToken,
    long expiresIn,
    String tokenType
) {}

