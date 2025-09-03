package com.halo.gateway.security;

import com.halo.gateway.jwt.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import lombok.*;
import lombok.extern.slf4j.*;
import org.springframework.security.authentication.*;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.*;
import org.springframework.util.*;
import org.springframework.web.filter.*;

import java.io.*;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String jwt = getJwtFromRequest(request);
			
			if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
				String username = jwtTokenProvider.getUsernameFromToken(jwt);
				String[] roles = jwtTokenProvider.getRolesFromToken(jwt);
				
				UserDetails userDetails = buildUserDetailsFromJwt(username, roles);
				
				UsernamePasswordAuthenticationToken authentication =
						new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authentication.setDetails(userDetails);
				
				SecurityContextHolder.getContext().setAuthentication(authentication);
				log.info("Authenticated user: {}", username);
			} else {
				log.warn("Invalid JWT token");
			}
		} catch (Exception e) {
			log.error("Could not set user authentication in security context", e);
		}
		// Continue the filter chain
		filterChain.doFilter(request, response);
	}
	
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}
		return null;
	}
	
	private UserDetails buildUserDetailsFromJwt(String username, String[] roles) {
		return org.springframework.security.core.userdetails.User
				.withUsername(username)
				.password("") // Password is not used in JWT authentication
				.roles(roles) // Set roles from JWT token
				.build();
	}
}
