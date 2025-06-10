package com.example.exception;

public class NoMessageFoundException extends RuntimeException {
    private String message;

    public NoMessageFoundException() {}

    public NoMessageFoundException(String msg) {
        super(msg);
        this.message = msg;
    }

    public String getMessage() {
        return this.message;
    }
}
