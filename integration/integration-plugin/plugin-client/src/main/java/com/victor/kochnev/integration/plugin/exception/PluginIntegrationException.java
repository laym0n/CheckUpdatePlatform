package com.victor.kochnev.integration.plugin.exception;

public class PluginIntegrationException extends RuntimeException {
    public PluginIntegrationException(String message) {
        super(message);
    }

    public PluginIntegrationException(String message, Throwable cause) {
        super(message, cause);
    }

    public PluginIntegrationException(Throwable cause) {
        super(cause);
    }
}
