package com.spring.restapi.information.validator;

import com.spring.restapi.information.dto.request.InformationRequest;
import com.spring.restapi.common.exception.ValidationException;
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
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }
    }

    private void validateName(String name, Map<String, String> errors) {
        if (!StringUtils.hasText(name)) {
            errors.put("name", "Name must not be empty");
        }
    }

}
