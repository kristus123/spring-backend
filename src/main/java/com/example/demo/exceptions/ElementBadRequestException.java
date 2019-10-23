package com.example.demo.exceptions;


public class ElementBadRequestException extends RuntimeException {
    public ElementBadRequestException(String msg) {
        super(msg);
    }
}
