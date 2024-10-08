package com.spring.restapi.user.model;

import lombok.Getter;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.Collections;


@Getter
public enum Role {
    ADMIN(1L),
    HR_MANAGER(2L),
    FINANCE_MANAGER(3L),
    INVENTORY_MANAGER(4L),
    SALES_MANAGER(5L),
    MANUFACTURING_MANAGER(6L),
    PROJECT_MANAGER(7L),
    CUSTOMER_SUPPORT(8L),
    PROCUREMENT_MANAGER(9L),
    IT_ADMIN(10L),
    USER(11L);

    private final Long value;

    private static final Map<Long, Role> ROLE_MAP = Collections.unmodifiableMap(
            Stream.of(Role.values()).collect(Collectors.toMap(Role::getValue, role -> role))
    );

    Role(Long value) {
        this.value = value;
    }

    public static Role fromValue(Long value) {
        Role role = ROLE_MAP.get(value);
        if (role == null) {
            throw new IllegalArgumentException("Unknown role value: " + value);
        }
        return role;
    }
}
