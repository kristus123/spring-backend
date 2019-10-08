package com.example.demo.exceptions;

public class AssociationNotFoundException extends RuntimeException {
    public AssociationNotFoundException(Integer id) {
        super("Could not find association with ID=" + id);
    }
}
