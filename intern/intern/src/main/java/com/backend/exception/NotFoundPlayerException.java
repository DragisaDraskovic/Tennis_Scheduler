package com.backend.exception;

public class NotFoundPlayerException extends RuntimeException{

    public NotFoundPlayerException(String errorMessage) {
        super(errorMessage);
    }
}
