package com.spring.restapi.service;

import com.spring.restapi.dto.request.UserRequest;
import com.spring.restapi.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    UserResponse getUser(Long id);
    UserResponse createUser(UserRequest request);
    UserResponse updateUser(Long id, UserRequest request);
    UserResponse deleteUser(Long id);
    List<UserResponse> getAllUsers();
    boolean userExists(Long id);

}
