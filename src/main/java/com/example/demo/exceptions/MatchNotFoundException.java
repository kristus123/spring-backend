package com.example.demo.exceptions;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(Integer id) {
        super("Could not find match with ID=" + id);
    }
}
