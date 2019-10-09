package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(Integer id) {
        super("Could not find match with ID=" + id);
    }
}
