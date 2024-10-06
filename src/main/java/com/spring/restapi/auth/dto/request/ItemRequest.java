package com.spring.restapi.auth.dto.request;

import lombok.Data;

@Data
public class ItemRequest {
    private String name;
    private String price;
}