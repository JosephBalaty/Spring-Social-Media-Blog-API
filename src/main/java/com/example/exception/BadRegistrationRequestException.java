package com.example.exception;

public class BadRegistrationRequestException extends RuntimeException {
    private String message;

    public BadRegistrationRequestException() {}

    public BadRegistrationRequestException(String msg) {
        super(msg);
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }
}
