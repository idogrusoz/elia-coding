package com.coding.elia.application.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class UnmappableIdException extends java.lang.IllegalArgumentException {
    public UnmappableIdException(String message) {
        super(message);
    }
}
