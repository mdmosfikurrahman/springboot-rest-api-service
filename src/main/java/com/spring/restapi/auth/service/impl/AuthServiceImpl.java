package com.spring.restapi.auth.service.impl;

import com.spring.restapi.auth.dto.response.JwtTokenResponse;
import com.spring.restapi.auth.exception.BadCredentialsException;
import com.spring.restapi.auth.model.TokenBlackList;
import com.spring.restapi.auth.repository.TokenBlackListRepository;
import com.spring.restapi.auth.service.JwtService;
import com.spring.restapi.auth.service.AuthService;
import com.spring.restapi.user.dto.request.UserRequest;
import com.spring.restapi.user.model.Users;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final TokenBlackListRepository repository;
    private final JwtService jwtService;

    @Override
    public JwtTokenResponse generateToken(String username, Long userId) {
        TokenBlackList existingToken = repository.findByUserIdAndInvalidatedAtIsNull(userId);

        if (existingToken != null) {
            repository.invalidateToken(existingToken.getToken(), userId, LocalDateTime.now());
        }

        Map<String, Object> claims = new HashMap<>();
        Date issuedAt = new Date(System.currentTimeMillis());
        Date expiration = new Date(System.currentTimeMillis() + 60 * 60 * 1000);
        String token = Jwts.builder()
                .claims()
                .add(claims)
                .subject(username)
                .issuedAt(issuedAt)
                .expiration(expiration)
                .and()
                .signWith(jwtService.getKey())
                .compact();

        TokenBlackList tokenBlacklist = TokenBlackList.builder()
                .token(token)
                .userId(userId)
                .invalidatedAt(null)
                .expiresAt(convertToLocalDateTimeViaInstant(expiration))
                .build();

        repository.save(tokenBlacklist);

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM, yyyy hh:mm:ss a");

        return new JwtTokenResponse(token, formatter.format(issuedAt), formatter.format(expiration));
    }

    @Override
    public JwtTokenResponse login(UserRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            Long userId = ((Users) authentication.getPrincipal()).getId();
            return generateToken(request.getUsername(), userId);
        } else {
            throw new BadCredentialsException("Authentication failed for user: " + request.getUsername());
        }
    }

    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
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
