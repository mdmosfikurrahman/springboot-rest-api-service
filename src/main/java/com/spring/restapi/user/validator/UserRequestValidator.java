package com.spring.restapi.user.validator;

import com.spring.restapi.user.dto.request.UserRequest;
import com.spring.restapi.common.exception.ValidationException;
import com.spring.restapi.user.model.Role;
import com.spring.restapi.user.model.Users;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserRequestValidator {

    private static final String REQUEST_KEY = "request";
    private static final String USERNAME_KEY = "username";
    private static final String PASSWORD_KEY = "password";
    private static final String ROLE_KEY = "role";

    public void validate(UserRequest request) {
        Map<String, String> errors = new HashMap<>();
        validateRequest(request, errors);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    public void validate(UserRequest request, Users existingUser) {
        Map<String, String> errors = new HashMap<>();
        validateRequest(request, errors);

        if (request != null) {
            validateUsername(request.getUsername(), existingUser.getUsername(), errors);
            validateRole(request.getRole(), existingUser.getRole(), errors);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateRequest(UserRequest request, Map<String, String> errors) {
        if (request == null) {
            errors.put(REQUEST_KEY, "Request must not be null");
        } else {
            validateUsername(request.getUsername(), errors);
            validatePassword(request.getPassword(), errors);
            validateRole(request.getRole(), errors);
        }
    }

    private void validateUsername(String username, Map<String, String> errors) {
        if (!StringUtils.hasText(username)) {
            errors.put(USERNAME_KEY, "Username must not be empty");
        }
    }

    private void validateUsername(String newUsername, String existingUsername, Map<String, String> errors) {
        validateUsername(newUsername, errors);
        if (StringUtils.hasText(newUsername) && !newUsername.equals(existingUsername)) {
            errors.put(USERNAME_KEY, "Username cannot be updated");
        }
    }

    private void validatePassword(String password, Map<String, String> errors) {
        if (!StringUtils.hasText(password)) {
            errors.put(PASSWORD_KEY, "Password must not be empty");
        }
    }

    private void validateRole(Role role, Map<String, String> errors) {
        if (role == null) {
            errors.put(ROLE_KEY, "Role must not be null");
        }
    }

    private void validateRole(Role newRole, Role existingRole, Map<String, String> errors) {
        validateRole(newRole, errors);
        if (newRole != null && !newRole.equals(existingRole)) {
            errors.put(ROLE_KEY, "Role cannot be updated");
        }
    }
}
