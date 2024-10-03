package com.spring.restapi.auth.service;

import javax.crypto.SecretKey;
import java.util.Date;

public interface JwtService {

    String extractUserName(String token);

    Date extractExpiration(String token);

    SecretKey getKey();

}
