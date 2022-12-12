package com.backend.exception;


public class InvalidDatesException extends RuntimeException{

    public InvalidDatesException(String message) {
        super(message);
    }

    public InvalidDatesException(String message, Throwable cause) {
        super(message, cause);
    }
}
