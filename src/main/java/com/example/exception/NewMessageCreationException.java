package com.example.exception;

public class NewMessageCreationException extends RuntimeException {
    private String message;

    public NewMessageCreationException() {}

    public NewMessageCreationException(String msg) {
        super(msg);
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }
}
