package com.spring.restapi.auth.service;

import com.spring.restapi.user.dto.request.UserRequest;
import com.spring.restapi.auth.dto.response.JwtTokenResponse;
import org.springframework.security.core.Authentication;

public interface AuthService {
    JwtTokenResponse login(UserRequest request);
    String logout(String authHeader, Authentication authentication);
    JwtTokenResponse generateToken(String username, Long userId);
}
