package com.spring.restapi.auth.service;

import javax.crypto.SecretKey;

public interface JwtService {

    String extractUserName(String token);

    SecretKey getKey();

}
