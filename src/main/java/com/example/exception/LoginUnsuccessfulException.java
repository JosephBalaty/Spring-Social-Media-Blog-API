package com.example.exception;

public class LoginUnsuccessfulException extends RuntimeException {
    private String message;

    public LoginUnsuccessfulException() {}

    public LoginUnsuccessfulException(String msg) {
        super(msg);
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }
}
