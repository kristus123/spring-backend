package com.example.demo.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String msg) {
        super(msg);
    }
}
