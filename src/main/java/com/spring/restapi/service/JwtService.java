package com.spring.restapi.service;

import javax.crypto.SecretKey;
import java.util.Date;

public interface JwtService {

    String extractUserName(String token);

    Date extractExpiration(String token);

    String generateSecretKey();

    SecretKey getKey();

}
