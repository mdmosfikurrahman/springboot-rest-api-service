package com.spring.restapi.user.service;

import com.spring.restapi.user.dto.request.UserRequest;
import com.spring.restapi.user.dto.response.UserResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserResponse getUser(Long id);
    UserResponse registerUser(UserRequest request);
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
    boolean userExists(Long id);
}
