package com.backend.handler;


import com.backend.exception.InvalidDatesException;
import com.backend.exception.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class UnauthorizedHandler {

    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(code = HttpStatus.UNAUTHORIZED)
    public String handleUnauthorized(UnauthorizedException exception){
        return exception.getMessage();
    }
}
