package com.spring.restapi.auth.controller;

import com.spring.restapi.auth.dto.response.JwtTokenResponse;
import com.spring.restapi.auth.service.AuthService;
import com.spring.restapi.common.response.RestResponse;
import com.spring.restapi.user.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public RestResponse<JwtTokenResponse> login(@RequestBody UserRequest request) {
        JwtTokenResponse jwtTokenResponse = authService.login(request);
        return RestResponse.success(HttpStatus.OK.value(), "Login successful", jwtTokenResponse);
    }

    @PostMapping("/logout")
    public RestResponse<String> logout(@RequestHeader("Authorization") String authHeader, Authentication authentication) {
        String response = authService.logout(authHeader, authentication);
        return RestResponse.success(HttpStatus.OK.value(), "Logout successful", response);
    }
}
