package com.example.demo.exceptions;


public class InvalidTeamRequestException extends RuntimeException {
    public InvalidTeamRequestException(Integer expectedId, Integer foundId) {
        super("Mismatch in expectedID=" + expectedId + " and foundID=" + foundId);
    }
}
