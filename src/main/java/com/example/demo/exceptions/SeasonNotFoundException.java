package com.example.demo.exceptions;

public class SeasonNotFoundException extends RuntimeException {
    public SeasonNotFoundException(Integer id) {
        super("Could not find season with ID=" + id);
    }
}
