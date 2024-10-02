package com.spring.restapi.service;

import com.spring.restapi.dto.request.UserRequest;
import com.spring.restapi.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserResponse getUser(Long id);
    UserResponse createUser(UserRequest request);
    UserResponse updateUser(Long id, UserRequest request);
    UserResponse deleteUser(Long id);
    boolean userExists(Long id);
}
