package com.longtv.halo.service;

import com.longtv.halo.entity.RefreshToken;
import com.longtv.halo.entity.User;
import com.longtv.halo.repository.RefreshTokenRepository;
import com.longtv.halo.repository.UserRepository;
import com.longtv.halo.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RefreshService {

    @Autowired
    private RefreshTokenRepository refreshTokenRepository;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserRepository userRepository;

    public RefreshToken saveRefreshToken(RefreshToken refreshToken) {
        RefreshToken refresh = refreshTokenRepository
                .findByToken(refreshToken.getToken())
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        jwtTokenProvider.verifyExpiration(refresh);

        User user = userRepository.findById(refresh.getUserId())
                .orElseThrow(() -> new UsernameNotFoundException(refresh.getId().toString()));
    }

}
