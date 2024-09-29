package com.spring.restapi.validator;

import com.spring.restapi.dto.request.InformationRequest;
import com.spring.restapi.exception.ValidationException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

@Component
public class InformationRequestValidator {

    public void validate(InformationRequest request) {
        Map<String, String> errors = new HashMap<>();

        if (request == null) {
            errors.put("request", "Request must not be null");
        } else {
            validateName(request.getName(), errors);
            // Add additional field validations here
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateName(String name, Map<String, String> errors) {
        if (!StringUtils.hasText(name)) {
            errors.put("name", "Name must not be empty");
        }
        // You can add more complex validations here (e.g., length, regex)
    }

    // Additional validation methods for other fields can be added here
}

