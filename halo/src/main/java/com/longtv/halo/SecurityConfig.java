package com.longtv.halo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.web.*;

@Configuration
public class SecurityConfig {

@Bean
public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	http
			.csrf(csrf -> csrf.disable())
			.authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
			.httpBasic(Customizer.withDefaults());
	
	return http.build();
}
}
