package com.example.exception;

public class UsernameAlreadyExistsException extends RuntimeException {
    private String message;

    public UsernameAlreadyExistsException() {}

    public UsernameAlreadyExistsException(String msg) {
        super(msg);
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }
}