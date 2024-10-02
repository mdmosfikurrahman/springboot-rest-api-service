package com.spring.restapi.dto.response;

public record JwtTokenResponse(String token, String username, String issuedAt, String expiration) {
}
