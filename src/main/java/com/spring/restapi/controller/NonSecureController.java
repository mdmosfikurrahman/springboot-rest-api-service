package com.spring.restapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/non-secure")
public class NonSecureController {

    // 1. GET method mapped directly to the base URL (/api/v1/non-secure)
    @GetMapping
    public ResponseEntity<String> baseEndpoint() {
        return ResponseEntity.ok("This is the base of the non-secure API!");
    }

    // 2. GET method with additional mapping (/api/v1/non-secure/welcome)
    @GetMapping("/welcome")
    public ResponseEntity<String> welcome() {
        return ResponseEntity.ok("Welcome to the Non-Secure API!");
    }

    // 3. GET method with a path variable (/api/v1/non-secure/items/{id})
    @GetMapping("/items/{id}")
    public ResponseEntity<String> getItemById(@PathVariable String id) {
        return ResponseEntity.ok("Item ID: " + id + ", Description: Mock Item Description");
    }

    // 4. POST method that accepts a request body (/api/v1/non-secure/items)
    @PostMapping("/items")
    public ResponseEntity<String> createItem(@RequestBody String item) {
        return ResponseEntity.ok("Item created: " + item);
    }

    // 5. GET method with request parameter (/api/v1/non-secure/search?name=example)
    @GetMapping("/search")
    public ResponseEntity<String> searchItemByName(@RequestParam String name) {
        return ResponseEntity.ok("Searching for item with name: " + name);
    }
}
