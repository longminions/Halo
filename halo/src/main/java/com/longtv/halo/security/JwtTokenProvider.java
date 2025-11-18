package com.longtv.halo.security;

import com.longtv.halo.config.JwtProperties;
import com.longtv.halo.entity.Acl;
import com.longtv.halo.entity.Permission;
import com.longtv.halo.entity.RefreshToken;
import com.longtv.halo.entity.User;
import com.longtv.halo.repository.AclRepository;
import com.longtv.halo.repository.RefreshTokenRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Autowired
    private final JwtProperties properties;

    @Autowired
    private AclRepository  aclRepository;

    @Autowired
    private final RefreshTokenRepository repo;

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    private Permission getRolesUser(User user) {
        Acl acl = aclRepository.getReferenceById(user.getId());
        return acl.getPermission();
    }
    
    public String generateAccessToken (User user) {
        Instant now = Instant.now();
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .claim("roles", getRolesUser(user).getAcls().get(0).getPermission())
                .issuer(properties.getIssuer())
                .issuedAt(Date.from(now))
                .expiration(Date.from(now.plusMillis(properties.getExpiration())))
                .signWith(secretKey())
                .compact();
    }

    public RefreshToken generateRefreshToken (User user) {
        Instant now = Instant.now();

        RefreshToken token = RefreshToken.builder()
                .userId(user.getId())
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(Duration.ofDays(30)))
                .revoked(false)
                .build();
        return repo.save(token);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            repo.delete(token);
            throw new RuntimeException("Refresh token expired");
        }
        return token;
    }
}
