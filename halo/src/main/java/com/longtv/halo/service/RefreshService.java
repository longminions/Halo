package com.longtv.halo.service;

import com.longtv.halo.config.JwtProperties;
import com.longtv.halo.dto.TokenResponse;
import com.longtv.halo.entity.RefreshToken;
import com.longtv.halo.entity.User;
import com.longtv.halo.repository.RefreshTokenRepository;
import com.longtv.halo.repository.UserRepository;
import com.longtv.halo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshService {

    private final RefreshTokenRepository refreshTokenRepository;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserRepository userRepository;

    private final JwtProperties jwtProperties;


    public TokenResponse saveRefreshToken(RefreshToken refreshToken, String accessToken) {
        Instant issuedAt = Instant.now();

        RefreshToken refresh = refreshTokenRepository
                .findByToken(refreshToken.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        //Kiểm tra token có bị revoked không
        if (refresh.isRevoked()) {
            throw new RuntimeException("Refresh token revoked");
        }

        //Lấy expriry
        jwtTokenProvider.verifyExpiration(refresh);

        //Lấy user
        User user = userRepository.findById(refresh.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException(refresh.getId().toString()));


        Instant accessTokenExpiresAt = issuedAt.plusSeconds(jwtProperties.getExpiration());
        String newAccessToken = jwtTokenProvider.generateAccessToken(user, issuedAt, accessTokenExpiresAt);

        //Cấp token mới
        RefreshToken rotated = jwtTokenProvider.rotateRefreshToken(refreshToken, user);

        //Đánh hết hạn cho token cũ
        Instant refreshTokenExpiresAt = issuedAt.plusSeconds(jwtProperties.getRefreshExpiration());

        return new TokenResponse(accessToken,
                rotateRefreshToken.getToken(),
                jwtProperties.getExpiration(),
                issuedAt, jwtProperties.getRefreshExpiration());
    }
}
