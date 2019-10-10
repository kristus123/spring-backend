package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException(Integer id) {
        super("Could not find team with ID=" + id);
    }
}
