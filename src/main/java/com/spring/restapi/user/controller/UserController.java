package com.spring.restapi.user.controller;

import com.spring.restapi.auth.dto.response.JwtTokenResponse;
import com.spring.restapi.common.response.RestResponse;
import com.spring.restapi.user.dto.request.PasswordUpdateRequest;
import com.spring.restapi.user.dto.request.UserRequest;
import com.spring.restapi.user.dto.response.UserResponse;
import com.spring.restapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public RestResponse<UserResponse> getUser(@PathVariable Long id) {
        UserResponse response = service.getUser(id);
        return RestResponse.success(HttpStatus.OK.value(), "Request Successful", response);
    }

    @PostMapping("/register")
    public RestResponse<UserResponse> registerUser(@RequestBody UserRequest request) {
        UserResponse response = service.registerUser(request);
        return RestResponse.success(HttpStatus.CREATED.value(), "User registered successfully", response);
    }

    @PutMapping("/{id}")
    public RestResponse<UserResponse> updateUser(@PathVariable Long id, @RequestBody UserRequest request) {
        UserResponse response = service.updateUser(id, request);
        return RestResponse.success(HttpStatus.OK.value(), "User updated successfully", response);
    }

    @PostMapping("/update-password/{id}")
    public RestResponse<JwtTokenResponse> updatePassword(@PathVariable Long id, @RequestBody PasswordUpdateRequest request) {
        JwtTokenResponse response = service.updatePassword(id, request);
        return RestResponse.success(HttpStatus.OK.value(), "Password updated successfully", response);
    }

    @DeleteMapping("/{id}")
    public RestResponse<Void> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return RestResponse.success(HttpStatus.OK.value(), "User deleted successfully", null);
    }

}
