package com.example.beomchulog.exception;

public abstract class BeomchulogException extends RuntimeException {

    public BeomchulogException(String message) {
        super(message);
    }

    public BeomchulogException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();
}
