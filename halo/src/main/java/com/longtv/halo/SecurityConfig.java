package com.longtv.halo;

import com.longtv.halo.serviceimpl.*;
import jakarta.servlet.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.*;
import org.springframework.security.authentication.dao.*;
import org.springframework.security.config.annotation.authentication.configuration.*;
import org.springframework.security.config.annotation.web.builders.*;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.config.http.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.*;
import org.springframework.security.crypto.password.*;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.*;
import org.springframework.web.servlet.config.annotation.*;
import com.longtv.halo.security.jwt.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	@Autowired
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	
	private final AuthEntryPointJwt unauthorizedHandler; // Xử lý lỗi 401 Unauthorized
	// Constructor injection
	public SecurityConfig(UserDetailsServiceImpl userDetailsServiceImpl, AuthEntryPointJwt unauthorizedHandler) {
		this.userDetailsServiceImpl = userDetailsServiceImpl;
		this.unauthorizedHandler = unauthorizedHandler;
	}
	
	@Bean
	public Filter authenticationJwtTokenFilter() { // Bean cho bộ lọc JWT
		return new AuthTokenFilter(); // Sẽ tạo lớp này sau
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService((UserDetailsService) userDetailsServiceImpl);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}
	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(csrf -> csrf.disable()) // Tắt CSRF cho API RESTful
				.exceptionHandling(exception -> exception.authenticationEntryPoint(unauthorizedHandler)) // Xử lý lỗi 401
				.sessionManagement(
						session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // KHÔNG DÙNG SESSION
				.authorizeHttpRequests(auth -> auth
						                               // Cho phép truy cập công khai vào các API xác thực và ping
						                               .requestMatchers("/api/auth/**")
						                               .permitAll() // API đăng nhập, đăng ký
						                               .requestMatchers("/api/ping").permitAll() // API ping
						                               // Cho phép truy cập Swagger/OpenAPI (nếu có)
						                               .requestMatchers("/v3/api-docs/**", "/swagger-ui/**",
								                               "/swagger-ui.html").permitAll().anyRequest().authenticated()
						// Tất cả các yêu cầu khác đều phải được xác thực
				);
		
		http.authenticationProvider(authenticationProvider());
		
		// Thêm bộ lọc JWT của bạn vào trước UsernamePasswordAuthenticationFilter
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
		
		return http.build();
	}
	
	// Cấu hình CORS của bạn vẫn giữ nguyên và đúng
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("http://localhost:3000", "http://localhost:5173")
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS").allowedHeaders("*")
						.allowCredentials(true).maxAge(3600);
			}
		};
	}
}