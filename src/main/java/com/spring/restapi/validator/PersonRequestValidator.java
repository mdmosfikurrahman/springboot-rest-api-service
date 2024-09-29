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
            validateUserId(request.getUserId(), errors);
            validateName(request.getFirstName(), "firstName", errors);
            validateName(request.getLastName(), "lastName", errors);
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateUserId(Long userId, Map<String, String> errors) {
        if (userId == null) {
            errors.put("userId", "userId" + " must not be null");
        } else if (userId <= 0) {
            errors.put("userId", "userId" + " must be a positive number");
        }
    }

    private void validateName(String name, String fieldName, Map<String, String> errors) {
        if (!StringUtils.hasText(name)) {
            errors.put(fieldName, fieldName + " must not be empty");
        }
    }

}
