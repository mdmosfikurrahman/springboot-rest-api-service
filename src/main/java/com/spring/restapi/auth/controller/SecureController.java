package com.spring.restapi.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/secure")
public class SecureController {

    // 1. GET method mapped directly to the base URL (/api/v1/secure)
    @GetMapping
    public ResponseEntity<String> baseEndpoint() {
        return ResponseEntity.ok("This is the base of the secure API!");
    }

    // 2. GET method with additional mapping (/api/v1/secure/status)
    @GetMapping("/status")
    public ResponseEntity<String> getStatus() {
        return ResponseEntity.ok("Secure API is up and running!");
    }

    // 3. GET method with a path variable (/api/v1/secure/users/{userId})
    @GetMapping("/users/{userId}")
    public ResponseEntity<String> getUserById(@PathVariable String userId) {
        return ResponseEntity.ok("User ID: " + userId + ", Name: Mock User Name");
    }

    // 4. POST method that accepts a request body (/api/v1/secure/users)
    @PostMapping("/users")
    public ResponseEntity<String> createUser(@RequestBody String user) {
        return ResponseEntity.ok("User created: " + user);
    }

    // 5. GET method with request parameter (/api/v1/secure/users/search?username=example)
    @GetMapping("/users/search")
    public ResponseEntity<String> searchUserByUsername(@RequestParam String username) {
        return ResponseEntity.ok("Searching for user with username: " + username);
    }
}
