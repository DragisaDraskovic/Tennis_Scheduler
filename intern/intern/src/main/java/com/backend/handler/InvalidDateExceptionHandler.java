package com.backend.handler;


import com.backend.exception.InvalidDatesException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class InvalidDateExceptionHandler {

    @ExceptionHandler(InvalidDatesException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public String handleInvalidDate(InvalidDatesException exception){
        return exception.getMessage();
    }
}
