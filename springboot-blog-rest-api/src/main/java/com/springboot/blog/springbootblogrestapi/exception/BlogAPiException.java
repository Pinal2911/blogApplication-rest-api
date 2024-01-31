package com.springboot.blog.springbootblogrestapi.exception;

import org.springframework.http.HttpStatus;

public class BlogAPiException extends RuntimeException{

    private HttpStatus status;
    private String message;


    public BlogAPiException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    public BlogAPiException(String message, HttpStatus status, String message1) {
        super(message);
        this.status = status;
        this.message = message1;
    }

    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
