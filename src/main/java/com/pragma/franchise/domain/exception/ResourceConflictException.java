package com.pragma.franchise.domain.exception;

public class ResourceConflictException extends RuntimeException{
    public ResourceConflictException(String message) {
        super(message);
    }

    public static ResourceConflictException of(String message) {
        return new ResourceConflictException(message);
    }
}
