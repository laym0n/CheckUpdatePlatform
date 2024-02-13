package com.victor.kochnev.core.exception;

import java.util.UUID;

public class PluginUsageNotPermittedException extends CoreException {
    public PluginUsageNotPermittedException(UUID pluginId) {
        this(pluginId, null);
    }

    public PluginUsageNotPermittedException(UUID pluginId, ResourceNotFoundException e) {
        super("Plugin usage with id  " + pluginId + " not permited", e);
    }
}
