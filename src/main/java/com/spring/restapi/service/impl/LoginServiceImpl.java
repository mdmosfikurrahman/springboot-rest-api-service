package com.spring.restapi.service.impl;

import com.spring.restapi.dto.request.UserRequest;
import com.spring.restapi.dto.response.JwtTokenResponse;
import com.spring.restapi.exception.BadCredentialsException;
import com.spring.restapi.model.TokenBlackList;
import com.spring.restapi.repository.TokenBlackListRepository;
import com.spring.restapi.service.JwtService;
import com.spring.restapi.service.LoginService;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final TokenBlackListRepository repository;
    private final JwtService jwtService;

    public JwtTokenResponse generateToken(String username) {
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

        SimpleDateFormat formatter = new SimpleDateFormat("dd MMMM, yyyy hh:mm:ss a");

        return new JwtTokenResponse(token, username, formatter.format(issuedAt), formatter.format(expiration));
    }

    @Override
    public JwtTokenResponse verify(UserRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return generateToken(request.getUsername());
        } else {
            throw new BadCredentialsException("Authentication failed for user: " + request.getUsername());
        }
    }

    private LocalDateTime convertToLocalDateTimeViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDateTime();
    }

    @Override
    public void invalidateToken(String token, Long userId) {
        Date expiration = jwtService.extractExpiration(token);

        TokenBlackList tokenBlacklist = TokenBlackList.builder()
                .token(token)
                .userId(userId)
                .invalidatedAt(LocalDateTime.now())
                .expiresAt(convertToLocalDateTimeViaInstant(expiration))
                .build();

        repository.save(tokenBlacklist);
    }
}
