package com.example.demo.exceptions;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(Integer id) {
        super("Could not find player with ID=" + id);
    }
}
