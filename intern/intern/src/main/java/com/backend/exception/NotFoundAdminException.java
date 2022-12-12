package com.backend.exception;

public class NotFoundAdminException extends RuntimeException{

    public  NotFoundAdminException(String errorMessage) {
        super(errorMessage);
    }
}
