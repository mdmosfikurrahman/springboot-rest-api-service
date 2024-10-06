package com.spring.restapi.auth.service;

import javax.crypto.SecretKey;

public interface JwtService {

    String extractEmail(String token);

    SecretKey getKey();

}
