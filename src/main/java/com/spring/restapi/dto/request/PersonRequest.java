package com.spring.restapi.dto.request;

import lombok.Data;

@Data
public class PersonRequest {

    private Long userId;
    private String firstName;
    private String lastName;

}