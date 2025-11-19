package com.longtv.halo.security;

import com.longtv.halo.config.JwtProperties;
import com.longtv.halo.dto.LoginRequest;
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
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private final JwtProperties properties;
    private final AclRepository  aclRepository;
    private final RefreshTokenRepository repo;

    private SecretKey secretKey() {
        return Keys.hmacShaKeyFor(properties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

    private List<Acl> getRolesUser(User user) {
        Acl acl = aclRepository.getReferenceById(user.getId());
        return acl.getPermission().getAcls();
    }
    
    public Map<Permission, Boolean> generateAccessToken (User user, String pathResource) {
        Map<Permission, Boolean> roleWithResource = getRolesUser(user).stream()
                .collect(Collectors.toMap(
                        Acl::getPermission,
                        Acl::getGranted
                ));

        Instant now = Instant.now();

        roleWithResource.forEach((permission, granted) -> {permission.getEndpointPermissions().forEach(endpointPermission -> {endpointPermission.getEndpoint().getPath().equals(pathResource)})})
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("userId", user.getId())
                .claim("roles",
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

    public RefreshToken rotateRefreshToken(RefreshToken oldToken, User user) {
        oldToken.setRevoked(true);
        repo.save(oldToken);

        RefreshToken newToken = new RefreshToken();
        newToken.setToken(UUID.randomUUID().toString());
        newToken.setUserId(user.getId());
        newToken.setExpiryDate(Instant.now().plus(7, ChronoUnit.DAYS));
        newToken.setRevoked(false);

        return repo.save(newToken);
    }
}
