package com.spring.restapi.auth.service;

import com.spring.restapi.user.dto.request.UserRequest;
import com.spring.restapi.auth.dto.response.JwtTokenResponse;

public interface LoginService {
    JwtTokenResponse verify(UserRequest request);
    void invalidateToken(String token, Long userId);
}
