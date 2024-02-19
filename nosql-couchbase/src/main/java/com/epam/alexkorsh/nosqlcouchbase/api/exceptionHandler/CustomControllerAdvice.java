package com.epam.alexkorsh.nosqlcouchbase.api.exceptionHandler;

import com.epam.alexkorsh.nosqlcouchbase.domain.exception.ResourceNotFoundException;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CustomControllerAdvice {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Response> handleResourceNotFoundException(ResourceNotFoundException e) {
        final String message = e.getMessage();
        Response response = new Response(String.format("%s : %s", LocalDateTime.now(), message));
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<Response> handleMongoWriteException() {
        final String message = "Write operation error occurred when trying to save new user";
        Response response = new Response(String.format("%s : %s", LocalDateTime.now(), message));
        return new ResponseEntity<>(response, HttpStatus.CONFLICT);
    }
}
