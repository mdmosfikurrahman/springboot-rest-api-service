package com.spring.restapi.auth.service;

import com.spring.restapi.auth.dto.request.LoginRequest;
import com.spring.restapi.auth.dto.response.JwtTokenResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    JwtTokenResponse login(LoginRequest request);
    String logout(String authHeader, Authentication authentication);
}
