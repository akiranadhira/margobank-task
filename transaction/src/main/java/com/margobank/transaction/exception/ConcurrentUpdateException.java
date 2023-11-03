package com.margobank.transaction.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConcurrentUpdateException extends RuntimeException{
    public ConcurrentUpdateException(String message) {
        super(message);
    }
}
