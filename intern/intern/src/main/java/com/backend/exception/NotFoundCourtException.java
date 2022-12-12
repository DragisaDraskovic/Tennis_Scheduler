package com.backend.exception;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class NotFoundCourtException extends RuntimeException {

    public NotFoundCourtException(String errorMessage) {

        super(errorMessage);
    }
}
