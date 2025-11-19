package com.halo.gateway.jwt;

import com.halo.gateway.common.CommonsKeys;
import com.halo.gateway.config.JwtProperties;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Component
@Slf4j
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;
    private final SecretKey secretKey;

    public JwtTokenProvider(JwtProperties jwtProperties) {
        this.jwtProperties = jwtProperties;
        this.secretKey = Keys.hmacShaKeyFor(jwtProperties.getSecret().getBytes(StandardCharsets.UTF_8));
    }

	/**
	 *
	 * @param userId
	 * @param username
	 * @param roles
	 * @return JWT token as a String
	 */
	public String generateToken(String userId, String username, String[] roles) {
		// Logic to generate JWT token using jwtProperties
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", username);
		claims.put("roles", roles);
        claims.put("id", userId);
		
		Date now = new Date();
		Date expirationDate = new Date(
				now.getTime() + jwtProperties.getExpiration() * CommonsKeys.MILLISECONDS_IN_A_SECOND);
		
		log.info("Generating JWT token for user: {}", username);
		
		return Jwts.builder()
					.setClaims(claims)
					.setSubject(userId)
					.setIssuer(jwtProperties.getIssuer())
					.setAudience("")
					.setIssuedAt(now)
					.setExpiration(expirationDate)
					.setId(UUID.randomUUID().toString())
					.signWith(secretKey)
					.compact();
	}
	
	
	/**
	 * Validates the JWT token.
	 *
	 * @param token the JWT token to validate
	 * @return true if the token is valid, false otherwise
	 */
    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;

        } catch (MalformedJwtException ex) {
            log.error("Invalid JWT token: {}", ex.getMessage());
        } catch (ExpiredJwtException ex) {
            log.error("JWT token is expired: {}", ex.getMessage());
        } catch (UnsupportedJwtException ex) {
            log.error("JWT token is unsupported: {}", ex.getMessage());
        } catch (IllegalArgumentException ex) {
            log.error("JWT claims string is empty or null: {}", ex.getMessage());
        } catch (JwtException ex) {
            log.error("JWT validation failed: {}", ex.getMessage());
        }

        return false;
    }
	
	public Claims getClaimsFromToken(String token) {
		// Logic to extract claims from the JWT token
		return Jwts.parser()
				.setSigningKey(jwtProperties.getSecret())
		        .build()
		        .parseClaimsJws(token)
				.getBody();
	}
	
	public String getUsernameFromToken(String token) {
		// Logic to extract username from the JWT token
		return (String) getClaimsFromToken(token).get("username");
	}
	
	public String[] getRolesFromToken(String token) {
		// Logic to extract roles from the JWT token
		List<?> rolesList = getClaimsFromToken(token).get("roles", List.class);
		if (rolesList == null && !rolesList.isEmpty()) {
			return new String[0]; // Return an empty array if no roles are found
		}
	
		return rolesList.stream()
				        .map(Object::toString)
				        .toArray(String[]::new); // Return an empty array if no roles are found
	}

    public Long getUserIdFromToken(String token) {
        Object userId = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId");
        return Long.valueOf(userId.toString());
    }
}
