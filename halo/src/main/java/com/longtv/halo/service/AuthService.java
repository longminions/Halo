package com.longtv.halo.service;

import com.longtv.halo.dto.LoginRequest;
import com.longtv.halo.dto.LoginResponse;
import com.longtv.halo.entity.RefreshToken;
import com.longtv.halo.entity.User;
import com.longtv.halo.repository.UserRepository;
import com.longtv.halo.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponse;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final BCryptPasswordEncoder passwordEncoder;
    

    public LoginResponse login (LoginRequest loginRequest) {

        User user = userRepository.findByUsername(loginRequest.username())
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));



        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    ErrorResponse.<LoginResponse>builder()
                            .success(false)
                            .message("Invalid username or password")
                            .errorCode("LOGIN_FAILED")
                            .build()
            );
        }

        String accessToken = jwtTokenProvider.generateAccessToken(user, loginRequest);
        RefreshToken refreshToken = jwtTokenProvider.generateRefreshToken(user);

    }

}
