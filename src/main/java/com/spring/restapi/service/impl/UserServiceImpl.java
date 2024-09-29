package com.spring.restapi.service.impl;

import com.spring.restapi.dto.request.UserRequest;
import com.spring.restapi.dto.response.UserResponse;
import com.spring.restapi.model.Users;
import com.spring.restapi.repository.UserRepository;
import com.spring.restapi.service.UserService;
import com.spring.restapi.validator.UserRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserRequestValidator validator;

    private UserResponse createUserResponse(String message, Users user) {
        return UserResponse.builder()
                .message(message)
                .data(user)
                .build();
    }

    private Users buildUserForCreate(UserRequest request) {
        return Users.builder()
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }

    private Users buildUserForUpdate(Users users, UserRequest request) {
        return Users.builder()
                .id(users.getId())
                .username(request.getUsername())
                .password(request.getPassword())
                .build();
    }

    private UserResponse updateExistingUser(Users existingUser, UserRequest request) {
        Users updatedPerson = buildUserForUpdate(existingUser, request);
        repository.save(updatedPerson);
        return createUserResponse("You made a PUT request to update id = " + updatedPerson.getId() + " with the following data!", updatedPerson);
    }

    @Override
    public UserResponse getUser(Long id) {
        return repository.findById(id)
                .map(user -> createUserResponse("User retrieved!", user))
                .orElseGet(() -> createUserResponse("User with ID " + id + " not found.", null));
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        validator.validate(request);
        Users user = buildUserForCreate(request);
        Users savedUser = repository.save(user);
        return createUserResponse("User created successfully!", savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        validator.validate(request);
        return repository.findById(id)
                .map(existingUser -> updateExistingUser(existingUser, request))
                .orElseGet(() -> createUserResponse("User with ID " + id + " not found.", null));
    }

    @Override
    public UserResponse deleteUser(Long id) {
        return repository.findById(id)
                .map(user -> {
                    repository.deleteById(id);
                    return createUserResponse("User with ID " + id + " deleted successfully!", null);
                })
                .orElseGet(() -> createUserResponse("User with ID " + id + " not found.", null));
    }

    @Override
    public boolean userExists(Long id) {
        return repository.existsById(id);
    }
}
