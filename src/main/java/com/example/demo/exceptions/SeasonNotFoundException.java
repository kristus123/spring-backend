package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SeasonNotFoundException extends RuntimeException {
    public SeasonNotFoundException(Integer id) {
        super("Could not find season with ID=" + id);
    }
}
