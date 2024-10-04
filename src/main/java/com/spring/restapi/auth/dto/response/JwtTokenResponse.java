package com.spring.restapi.auth.dto.response;

public record JwtTokenResponse(String token, String issuedAt, String expiration) {
}
