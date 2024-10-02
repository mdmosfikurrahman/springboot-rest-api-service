package com.spring.restapi.service.impl;

import com.spring.restapi.dto.request.UserRequest;
import com.spring.restapi.dto.response.UserResponse;
import com.spring.restapi.model.UserPrincipal;
import com.spring.restapi.model.Users;
import com.spring.restapi.repository.UserRepository;
import com.spring.restapi.service.UserService;
import com.spring.restapi.validator.UserRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserRequestValidator validator;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    private static final String USER_CREATED_MESSAGE = "User with ID %s is created.";
    private static final String USER_UPDATED_MESSAGE = "User with ID %s is updated.";
    private static final String USER_DELETED_MESSAGE = "User with ID %s is deleted.";
    private static final String USER_RECEIVED_MESSAGE = "User with ID %s is received.";
    private static final String USER_NOT_FOUND_MESSAGE = "User with ID %s not found.";

    private UserResponse createUserResponse(String message, Users user) {
        return UserResponse.builder()
                .message(message)
                .data(user)
                .build();
    }

    private Users buildUserForCreate(UserRequest request) {
        return Users.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    private Users buildUserForUpdate(Users users, UserRequest request) {
        return Users.builder()
                .id(users.getId())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
    }

    private UserResponse updateExistingUser(Users existingUser, UserRequest request) {
        Users updatedPerson = buildUserForUpdate(existingUser, request);
        repository.save(updatedPerson);
        return createUserResponse(String.format(USER_UPDATED_MESSAGE, updatedPerson.getId()), updatedPerson);
    }

    @Override
    public UserResponse getUser(Long id) {
        return repository.findById(id)
                .map(user -> createUserResponse(String.format(USER_RECEIVED_MESSAGE, id), user))
                .orElseGet(() -> createUserResponse(String.format(USER_NOT_FOUND_MESSAGE, id), null));
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        validator.validate(request);
        Users user = buildUserForCreate(request);
        Users savedUser = repository.save(user);
        return createUserResponse(String.format(USER_CREATED_MESSAGE, savedUser.getId()), savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        validator.validate(request);
        return repository.findById(id)
                .map(existingUser -> updateExistingUser(existingUser, request))
                .orElseGet(() -> createUserResponse(String.format(USER_NOT_FOUND_MESSAGE, id), null));
    }

    @Override
    public UserResponse deleteUser(Long id) {
        return repository.findById(id)
                .map(user -> {
                    repository.deleteById(id);
                    return createUserResponse(String.format(USER_DELETED_MESSAGE, id), null);
                })
                .orElseGet(() -> createUserResponse(String.format(USER_NOT_FOUND_MESSAGE, id), null));
    }

    @Override
    public boolean userExists(Long id) {
        return repository.existsById(id);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new UserPrincipal(user);
    }
}
