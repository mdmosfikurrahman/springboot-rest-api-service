package com.spring.restapi.dto.request;

import lombok.Data;

@Data
public class PersonRequest {

    private String firstName;
    private String lastName;
    private String username;
    private String password;

}