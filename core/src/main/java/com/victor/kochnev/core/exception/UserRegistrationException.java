package com.victor.kochnev.core.exception;

public class UserRegistrationException extends CoreException {
    public UserRegistrationException(String email) {
        super("User with email " + email + "already registered");
    }
}
