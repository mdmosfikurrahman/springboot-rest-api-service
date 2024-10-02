package com.spring.restapi.controller;

import com.spring.restapi.dto.request.UserRequest;
import com.spring.restapi.dto.response.JwtTokenResponse;
import com.spring.restapi.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class LoginController {

    private final LoginService service;

    @PostMapping("/login")
    public JwtTokenResponse login(@RequestBody UserRequest request) {
        return service.verify(request);
    }

}
