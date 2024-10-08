package com.spring.restapi.auth.service.impl;

import com.spring.restapi.auth.dto.request.LoginRequest;
import com.spring.restapi.auth.dto.response.JwtTokenResponse;
import com.spring.restapi.auth.exception.BadCredentialsException;
import com.spring.restapi.auth.repository.TokenBlackListRepository;
import com.spring.restapi.auth.service.AuthService;
import com.spring.restapi.auth.service.TokenService;
import com.spring.restapi.auth.validator.LoginRequestValidator;
import com.spring.restapi.user.model.Users;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenBlackListRepository repository;
    private final TokenService tokenService;
    private final LoginRequestValidator validator;

    @Override
    public JwtTokenResponse login(LoginRequest request) {
        validator.validate(request);
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            Long userId = ((Users) authentication.getPrincipal()).getId();
            return tokenService.generateToken(request.getEmail(), userId);
        } else {
            throw new BadCredentialsException("Authentication failed for " + request.getEmail());
        }
    }

    @Override
    @Transactional
    public String logout(String authHeader, Authentication authentication) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = ((Users) authentication.getPrincipal()).getId();
            repository.invalidateToken(token, userId, LocalDateTime.now());
            return "User logged out successfully.";
        }
        return "Invalid authorization header.";
    }
}
