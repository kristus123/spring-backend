package com.example.demo.exceptions;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Integer id) {
        super("Could not find team with ID=" + id);
    }
}
