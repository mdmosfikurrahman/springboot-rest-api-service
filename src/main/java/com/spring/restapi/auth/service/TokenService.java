package com.spring.restapi.auth.service;

import org.springframework.security.core.userdetails.UserDetails;

@SuppressWarnings("unused")
public interface TokenService {

    boolean isTokenBlacklisted(String token);

    boolean isTokenExpired(String token);

    boolean validateToken(String token, UserDetails userDetails);

    void cleanUpExpiredTokens();

    String extractTokenExpiration(String token);
}
