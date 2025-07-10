package com.longtv.halo.security.jwt;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.*;
import io.jsonwebtoken.security.*;
import org.slf4j.*;
import org.springframework.security.core.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.beans.factory.annotation.Value;
import java.security.*;
import java.util.*;

@Component // Đánh dấu là một Spring component để Spring tự động tạo và quản lý
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);
	
	@Value("${longtv.app.jwtSecret}")
	private String jwtSecret;
	
	@Value("${longtv.app.jwtExpirationMs}")
	private int jwtExpirationMs;
	
	// Hàm tạo JWT token
	public String generateJwtToken(Authentication authentication) {
		UserDetails userPrincipal = (UserDetails) authentication.getPrincipal(); // Lấy thông tin người dùng từ Authentication
		
		return Jwts.builder()
				       .setSubject((userPrincipal.getUsername())) // Đặt username làm subject của token
				       .setIssuedAt(new Date()) // Thời gian phát hành token
				       .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)) // Thời gian hết hạn
				       .signWith(key(), SignatureAlgorithm.HS512) // Ký token bằng secret key và thuật toán HS512
				       .compact(); // Nén lại thành chuỗi JWT
	}
	
	// Lấy Key từ secret string
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	// Lấy username từ JWT token
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser().setSigningKey(key()).build()
				       .parseClaimsJws(token).getBody().getSubject();
	}
	
	// Xác thực JWT token
	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser().setSigningKey(key()).build().parse(authToken);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}
		return false;
	}
}