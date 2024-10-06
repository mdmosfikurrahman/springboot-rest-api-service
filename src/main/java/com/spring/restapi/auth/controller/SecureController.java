package com.spring.restapi.auth.controller;

import com.spring.restapi.auth.dto.request.SecureUserRequest;
import com.spring.restapi.common.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/secure")
public class SecureController {

    private static final String SUCCESS_MESSAGE = "Request Successful";

    @GetMapping
    public RestResponse<String> baseEndpoint() {
        return RestResponse.success(HttpStatus.OK.value(), SUCCESS_MESSAGE, "This is the base of the secure API!");
    }

    @GetMapping("/status")
    public RestResponse<String> getStatus() {
        return RestResponse.success(HttpStatus.OK.value(), SUCCESS_MESSAGE, "Secure API is up and running!");
    }

    @GetMapping("/users/{userId}")
    public RestResponse<String> getUserById(@PathVariable String userId) {
        String userName = "Mock User Name";
        return RestResponse.success(HttpStatus.OK.value(), SUCCESS_MESSAGE, "User ID: " + userId + ", Name: " + userName);
    }

    @PostMapping("/users")
    public RestResponse<SecureUserRequest> createUser(@RequestBody SecureUserRequest secureUserRequest) {
        return RestResponse.success(HttpStatus.CREATED.value(), "User created successfully", secureUserRequest);
    }

    @GetMapping("/users/search")
    public RestResponse<String> searchUserByUsername(@RequestParam String username) {
        return RestResponse.success(HttpStatus.OK.value(), SUCCESS_MESSAGE, "Searching for user with username: " + username);
    }
}