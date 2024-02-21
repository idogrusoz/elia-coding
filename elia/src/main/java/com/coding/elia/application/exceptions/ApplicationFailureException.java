package com.coding.elia.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ApplicationFailureException extends RuntimeException{
    public ApplicationFailureException(String message) {
        super(message);
    }
}
