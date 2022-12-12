package com.backend.exception;

public class UnsupportedOperationException extends  RuntimeException {

    public UnsupportedOperationException(String errorMessage) {
        super(errorMessage);
    }
}
