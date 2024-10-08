package com.spring.restapi.auth.controller;

import com.spring.restapi.auth.dto.request.ItemRequest;
import com.spring.restapi.common.exception.ValidationException;
import com.spring.restapi.common.response.RestResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/non-secure")
public class NonSecureController {

    private static final String SUCCESS_MESSAGE = "Request Successful";

    @GetMapping
    public RestResponse<String> baseEndpoint() {
        return RestResponse.success(HttpStatus.OK.value(), SUCCESS_MESSAGE, "This is the base of the non-secure API!");
    }

    @GetMapping("/welcome")
    public RestResponse<String> welcome() {
        return RestResponse.success(HttpStatus.OK.value(), SUCCESS_MESSAGE, "Welcome to the Non-Secure API!");
    }

    @GetMapping("/items/{id}")
    public RestResponse<String> getItemById(@PathVariable String id) {
        String description = "Mock Item Description";
        return RestResponse.success(HttpStatus.OK.value(), SUCCESS_MESSAGE, "Item ID: " + id + ", Description: " + description);
    }

    @PostMapping("/items")
    public RestResponse<ItemRequest> createItem(@RequestBody ItemRequest itemRequest) {
        Map<String, String> errors = validateItemRequest(itemRequest);

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        return RestResponse.success(HttpStatus.CREATED.value(), "Item created successfully", itemRequest);
    }

    @GetMapping("/search")
    public RestResponse<String> searchItemByName(@RequestParam String name) {
        return RestResponse.success(HttpStatus.OK.value(), SUCCESS_MESSAGE, "Searching for item with name: " + name);
    }

    private Map<String, String> validateItemRequest(ItemRequest itemRequest) {
        Map<String, String> errors = new HashMap<>();

        if (itemRequest.getName() == null || itemRequest.getName().isEmpty()) {
            errors.put("name", "name is required");
        }

        if (itemRequest.getPrice() == null || itemRequest.getPrice().isEmpty()) {
            errors.put("price", "price is required");
        }

        return errors;
    }

}
