package com.victor.kochnev.rest.presenters.exception;

public class RestPresentersException extends RuntimeException {
    public RestPresentersException(String message) {
        super(message);
    }

    public RestPresentersException(String message, Throwable cause) {
        super(message, cause);
    }
}
