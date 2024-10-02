package com.spring.restapi.service.impl;

import com.spring.restapi.repository.TokenBlackListRepository;
import com.spring.restapi.service.JwtService;
import com.spring.restapi.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenServiceImpl implements TokenService {

    private final JwtService jwtService;
    private final TokenBlackListRepository repository;

    @Override
    public boolean isTokenBlacklisted(String token) {
        return repository.existsByToken(token);
    }

    @Override
    public boolean isTokenExpired(String token) {
        return jwtService.extractExpiration(token).before(new Date());
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = jwtService.extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

}
