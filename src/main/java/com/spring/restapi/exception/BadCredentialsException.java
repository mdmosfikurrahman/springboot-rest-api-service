package com.spring.restapi.exception;

import lombok.Getter;

@Getter
public class BadCredentialsException extends RuntimeException{
    private final String message;

    public BadCredentialsException(String message) {
        super(message);
        this.message = message;
    }
}
