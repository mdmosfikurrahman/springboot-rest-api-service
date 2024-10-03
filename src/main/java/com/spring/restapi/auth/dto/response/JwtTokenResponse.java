package com.spring.restapi.auth.dto.response;

public record JwtTokenResponse(String token, String username, String issuedAt, String expiration) {
}
