package com.halo.gateway.security;

import com.halo.gateway.jwt.JwtTokenProvider;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
@Slf4j
public class JwtGlobalFilter implements GlobalFilter, Ordered {

    private final JwtTokenProvider jwtTokenProvider;


    @PostConstruct
    public void init() {
        log.info(">>> JwtGlobalFilter INIT");
    }


    public JwtGlobalFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info(">>> JwtGlobalFilter START path={}", exchange.getRequest().getURI().getPath());
        
        String path = exchange.getRequest().getURI().getPath();
        // bypass login and actuator
        if (path.startsWith("/auth/login") || path.startsWith("/actuator")) {
            return chain.filter(exchange);
        }

        String authencationHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        if (authencationHeader == null || !authencationHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }

        // Có token hợp lệ → kiểm tra
        String jwt = authencationHeader.substring(7);

        if (jwtTokenProvider.validateToken(jwt)) {

            String username = jwtTokenProvider.getUsernameFromToken(jwt);
            String[] roles = jwtTokenProvider.getRolesFromToken(jwt);
            Long userId = jwtTokenProvider.getUserIdFromToken(jwt);

            log.info("username = {}, roles = {}", username, roles);

            exchange = exchange.mutate().request(
                    exchange.getRequest().mutate()
                            .header("X-Auth-UserId", String.valueOf(userId))
                            .header("X-Auth-Username", username)
                            .header("X-Auth-Roles", String.join(",", roles))
                            .build()
            ).build();
        } else {
            log.warn("Invalid token");
        }

        return chain.filter(exchange);
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
    
    @Override
    public int getOrder() {
        return -1;
    }
}
