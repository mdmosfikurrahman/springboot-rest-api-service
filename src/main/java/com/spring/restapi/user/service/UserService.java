package com.spring.restapi.user.service;

import com.spring.restapi.auth.dto.response.JwtTokenResponse;
import com.spring.restapi.user.dto.request.PasswordUpdateRequest;
import com.spring.restapi.user.dto.request.UserRequest;
import com.spring.restapi.user.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserResponse getUser(Long id);
    UserResponse registerUser(UserRequest request);
    UserResponse updateUser(Long id, UserRequest request);
    JwtTokenResponse updatePassword(Long id, PasswordUpdateRequest request);
    void deleteUser(Long id);
    boolean userExists(Long id);
}
