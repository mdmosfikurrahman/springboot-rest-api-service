package com.spring.restapi.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface TokenService {

    boolean isTokenBlacklisted(String token);

    boolean isTokenExpired(String token);

    boolean validateToken(String token, UserDetails userDetails);

}
