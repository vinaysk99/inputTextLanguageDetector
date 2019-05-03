package com.vk.languageDetector.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class InvalidDataException extends RuntimeException {

    private String message;

    public InvalidDataException() {
        super();
    }

    public InvalidDataException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
