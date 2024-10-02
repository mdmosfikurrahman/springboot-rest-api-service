package com.spring.restapi.service.impl;

import com.spring.restapi.dto.request.UserRequest;
import com.spring.restapi.dto.response.JwtTokenResponse;
import com.spring.restapi.exception.BadCredentialsException;
import com.spring.restapi.jwt.JwtService;
import com.spring.restapi.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public JwtTokenResponse verify(UserRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
        );

        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(request.getUsername());
        } else {
            throw new BadCredentialsException("Authentication failed for user: " + request.getUsername());
        }
    }
}
