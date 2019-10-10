package com.example.demo.exceptions;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(Integer id) {
        super("Could not find person with ID=" + id);
    }
}
