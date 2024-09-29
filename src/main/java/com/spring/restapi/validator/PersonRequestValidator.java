package com.spring.restapi.validator;

import com.spring.restapi.dto.request.PersonRequest;
import com.spring.restapi.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class PersonRequestValidator {

    public void validate(PersonRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (request == null) {
            errors.put("request", "Request must not be null");
        } else {
            validateName(request.getFirstName(), "firstName", errors);
            validateName(request.getLastName(), "lastName", errors);
            validateUsername(request.getUsername(), errors);
            validatePassword(request.getPassword(), errors);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateName(String name, String fieldName, Map<String, String> errors) {
        if (!StringUtils.hasText(name)) {
            errors.put(fieldName, fieldName + " must not be empty");
        }
    }

    private void validateUsername(String username, Map<String, String> errors) {
        if (!StringUtils.hasText(username)) {
            errors.put("username", "Username must not be empty");
        }
    }

    private void validatePassword(String password, Map<String, String> errors) {
        if (!StringUtils.hasText(password)) {
            errors.put("password", "Password must not be empty");
        }
    }
}
