package com.victor.kochnev.integration.mail.exception;

public class MailIntegrationException extends RuntimeException {
    public MailIntegrationException(Throwable cause) {
        super(cause);
    }

    public MailIntegrationException(String message) {
        super(message);
    }

    public MailIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }
}
