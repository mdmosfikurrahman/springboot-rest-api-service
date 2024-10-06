package com.spring.restapi.user.service.impl;

import com.spring.restapi.auth.dto.response.JwtTokenResponse;
import com.spring.restapi.auth.service.TokenService;
import com.spring.restapi.common.exception.NotFoundException;
import com.spring.restapi.user.dto.request.PasswordUpdateRequest;
import com.spring.restapi.user.dto.request.UserRequest;
import com.spring.restapi.user.dto.response.UserResponse;
import com.spring.restapi.user.model.Users;
import com.spring.restapi.user.repository.UserRepository;
import com.spring.restapi.user.service.UserService;
import com.spring.restapi.user.validator.UserRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final TokenService tokenService;
    private final UserRepository repository;
    private final UserRequestValidator validator;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    private static final String USER_NOT_FOUND_MESSAGE = "User with ID %s not found.";

    private UserResponse createUserResponse(Users user) {
        return UserResponse.builder()
                .username(user.getUsername())
                .role(user.getRole())
                .build();
    }

    private Users buildUserForCreate(UserRequest request) {
        return Users.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
    }

    private Users buildUserForUpdate(Users users, UserRequest request) {
        return Users.builder()
                .id(users.getId())
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
    }

    private UserResponse updateExistingUser(Users existingUser, UserRequest request) {
        Users updatedPerson = buildUserForUpdate(existingUser, request);
        repository.save(updatedPerson);
        return createUserResponse(updatedPerson);
    }

    @Override
    public UserResponse getUser(Long id) {
        return repository.findById(id)
                .map(this::createUserResponse)
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public UserResponse registerUser(UserRequest request) {
        validator.validate(request);
        Users user = buildUserForCreate(request);
        Users savedUser = repository.save(user);
        return createUserResponse(savedUser);
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        validator.validate(request);
        return repository.findById(id)
                .map(existingUser -> updateExistingUser(existingUser, request))
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id)));
    }

    @Override
    public void deleteUser(Long id) {
        repository.findById(id)
                .ifPresentOrElse(
                        user -> repository.deleteById(id),
                        () -> { throw new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id)); }
                );
    }

    @Override
    public boolean userExists(Long id) {
        return repository.existsById(id);
    }

    @Override
    public JwtTokenResponse updatePassword(Long id, PasswordUpdateRequest request) {
        Users existingUser = repository.findById(id)
                .orElseThrow(() -> new NotFoundException(String.format(USER_NOT_FOUND_MESSAGE, id)));

        existingUser.setPassword(passwordEncoder.encode(request.getNewPassword()));
        repository.save(existingUser);
        return tokenService.generateToken(existingUser.getUsername(), existingUser.getId());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = repository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found");
        }
        return user;
    }
}
