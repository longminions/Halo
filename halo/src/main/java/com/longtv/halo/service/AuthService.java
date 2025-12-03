package com.longtv.halo.service;

import com.longtv.halo.dto.LoginRequest;
import com.longtv.halo.dto.LoginResponse;
import com.longtv.halo.entity.RefreshToken;
import com.longtv.halo.entity.User;
import com.longtv.halo.repository.UserRepository;
import com.longtv.halo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    

    public LoginResponse login (LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));

        RefreshToken refreshToken = jwtTokenProvider.generateRefreshToken(user);
        return null;
    }

}
