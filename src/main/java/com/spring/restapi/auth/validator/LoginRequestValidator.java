package com.spring.restapi.auth.validator;

import com.spring.restapi.auth.dto.request.LoginRequest;
import com.spring.restapi.common.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class LoginRequestValidator {

    public void validate(LoginRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (request == null) {
            errors.put("request", "Request must not be null");
        } else {
            validateEmail(request.getEmail(), errors);
            validatePassword(request.getPassword(), errors);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateEmail(String fieldName, Map<String, String> errors) {
        if (!StringUtils.hasText(fieldName)) {
            errors.put("email", "Email must not be empty");
        }
    }

    private void validatePassword(String fieldName, Map<String, String> errors) {
        if (!StringUtils.hasText(fieldName)) {
            errors.put("password", "Password must not be empty");
        }
    }

}
