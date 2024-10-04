package com.spring.restapi.user.model;

import lombok.Getter;

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

    Role(Long value) {
        this.value = value;
    }

    public static Role fromValue(Long value) {
        for (Role role : Role.values()) {
            if (role.getValue().equals(value)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Unknown role value: " + value);
    }
}
