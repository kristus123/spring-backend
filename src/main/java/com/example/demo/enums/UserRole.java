package com.example.demo.enums;

import lombok.Getter;
import org.springframework.security.core.userdetails.User;

@Getter
public enum UserRole {
    ADMINISTRATOR("ADMINISTRATOR"), STANDARD("STANDARD");

    private String role;

    UserRole(String role) {
        this.role = role;
    }
}
