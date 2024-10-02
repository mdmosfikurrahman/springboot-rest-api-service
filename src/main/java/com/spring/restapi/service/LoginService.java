package com.spring.restapi.service;

import com.spring.restapi.dto.request.UserRequest;
import com.spring.restapi.dto.response.JwtTokenResponse;

public interface LoginService {
    JwtTokenResponse verify(UserRequest request);
    void invalidateToken(String token, Long userId);
}
