package com.victor.kochnev.integration.telegram.exception;

public class TelegramIntegrationException extends RuntimeException {
    public TelegramIntegrationException(String message) {
        super(message);
    }

    public TelegramIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public TelegramIntegrationException(Throwable cause) {
        super(cause);
    }
}
