package com.victor.kochnev.core.exception;

import java.util.UUID;

public class PluginUsageNotPermittedException extends CoreException {
    public PluginUsageNotPermittedException(UUID id) {
        super("Plugin usage with id  " + id + " not permited");
    }
}
