package com.andersen.orange.exception;

public class SameTeamException extends RuntimeException{
    public SameTeamException(String message) {
        super(message);
    }
}
