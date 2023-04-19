package com.epam.alexkorsh.nosqlcouchbase.domain.exception;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(String userNotFound) {
        super(userNotFound);
    }
}
