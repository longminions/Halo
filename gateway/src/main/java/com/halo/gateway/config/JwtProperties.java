package com.halo.gateway.config;

import lombok.*;
import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;

/**
 * Configuration properties for JWT.
 * This class holds the properties required for JWT token generation and validation.
 * It includes the secret key, expiration time, and issuer.
 * It is annotated with @ConfigurationProperties to bind the properties from application configuration files.
 * * @author Longtv
 * @version 1.0
 */
@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {
	private String secret;
	private long expiration;
	private String issuer;
}
