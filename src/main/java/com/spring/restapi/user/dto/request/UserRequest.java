package com.spring.restapi.user.dto.request;

import com.spring.restapi.user.model.Role;
import lombok.Data;

@Data
public class UserRequest {

    private String username;
    private String password;
    private Role role;

}
