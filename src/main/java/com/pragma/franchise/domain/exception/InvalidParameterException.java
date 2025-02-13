package com.pragma.franchise.domain.exception;

public class InvalidParameterException extends RuntimeException {
    public InvalidParameterException(String message) {
        super(message);
    }

    public static InvalidParameterException of(String message) {
        return new InvalidParameterException(message);
    }
}
