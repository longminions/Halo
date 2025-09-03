package com.halo.gateway.jwt;

import com.halo.gateway.common.*;
import com.halo.gateway.config.*;
import io.jsonwebtoken.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.stereotype.*;

import java.util.*;

@Data
@Component
@AllArgsConstructor
@Slf4j
public class JwtTokenProvider {

	private final JwtProperties jwtProperties;

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
		claims.put("roles", roles); // One user can have multiple roles
		
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
					.signWith(SignatureAlgorithm.HS256, jwtProperties.getSecret())
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
					.setSigningKey(jwtProperties.getSecret()).build()
					.parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException ex) {
			log.error("Invalid JWT token: {}", ex.getMessage());
		} catch (ExpiredJwtException ex) {
			log.error("JWT token is expired: {}", ex.getMessage());
		} catch (UnsupportedJwtException ex) {
			log.error("JWT token is unsupported: {}", ex.getMessage());
		} catch (IllegalArgumentException ex) {
			log.error("JWT claims string is empty or null: {}", ex.getMessage());
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
	
	public String getUserIdFromToken(String token) {
		// Logic to extract user ID from the JWT token
		return getClaimsFromToken(token).getSubject();
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
}
