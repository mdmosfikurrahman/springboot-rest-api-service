package com.spring.restapi.controller;

import com.spring.restapi.dto.request.UserRequest;
import com.spring.restapi.dto.response.JwtTokenResponse;
import com.spring.restapi.model.UserPrincipal;
import com.spring.restapi.model.Users;
import com.spring.restapi.service.LoginService;
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
        return loginService.verify(request);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String authHeader, Authentication authentication) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Long userId = ((UserPrincipal) authentication.getPrincipal()).getId();
            loginService.invalidateToken(token, userId);
            return ResponseEntity.ok("User logged out successfully.");
        }
        return ResponseEntity.badRequest().body("Invalid authorization header.");
    }

}
