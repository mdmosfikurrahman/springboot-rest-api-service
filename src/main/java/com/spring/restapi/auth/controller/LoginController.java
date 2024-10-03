package com.spring.restapi.auth.controller;

import com.spring.restapi.auth.dto.response.JwtTokenResponse;
import com.spring.restapi.auth.service.LoginService;
import com.spring.restapi.user.dto.request.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    public JwtTokenResponse login(@RequestBody UserRequest request) {
        return loginService.login(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader, Authentication authentication) {
        String response = loginService.logout(authHeader, authentication);
        return ResponseEntity.ok(response);
    }

}
