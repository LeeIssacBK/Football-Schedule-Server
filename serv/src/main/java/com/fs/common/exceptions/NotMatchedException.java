package com.fs.common.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class NotMatchedException extends RuntimeException {

    public NotMatchedException(String message) {
        super(message);
    }

}
