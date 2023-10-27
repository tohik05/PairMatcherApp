package com.andersen.orange.exception;

public class TeamHasAlreadyExistException extends RuntimeException {
    public TeamHasAlreadyExistException(String message) {
        super(message);
    }
}
