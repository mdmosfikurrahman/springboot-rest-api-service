package com.spring.restapi.auth.service.impl;

import com.spring.restapi.auth.model.TokenBlackList;
import com.spring.restapi.auth.repository.TokenBlackListRepository;
import com.spring.restapi.auth.service.JwtService;
import com.spring.restapi.auth.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

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
        TokenBlackList tokenBlackList = repository.findByToken(token);
        if (tokenBlackList != null) {
            return tokenBlackList.getExpiresAt().isBefore(LocalDateTime.now());
        }
        return true;
    }

    @Override
    public boolean validateToken(String token, UserDetails userDetails) {
        final String userName = jwtService.extractUserName(token);
        return (userName.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    @Scheduled(cron = "0 0 0 * * ?")
    @Transactional
    @Override
    public void cleanUpExpiredTokens() {
        List<TokenBlackList> tokens = repository.findAll();

        tokens.stream()
                .filter(tokenBlackList -> isTokenExpired(tokenBlackList.getToken()))
                .forEach(token -> {
                    token.setInvalidatedAt(token.getExpiresAt());
                    repository.save(token);
                });
    }

    @Override
    public String extractTokenExpiration(String token) {
        TokenBlackList tokenBlackList = repository.findByToken(token);
        LocalDateTime expirationTime = tokenBlackList.getExpiresAt();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMMM, yyyy hh:mm:ss a");
        return expirationTime.format(formatter);
    }

}