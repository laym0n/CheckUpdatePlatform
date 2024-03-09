package com.victor.kochnev.core.exception;

import lombok.Data;

import java.util.UUID;

@Data
public class ResourceDescriptionParseException extends CoreException {
    private String description;
    private UUID pluginId;

    public ResourceDescriptionParseException(String description, UUID pluginId) {
        super("Description " + description + " can not be parsed by plugin with id " + pluginId);
        this.description = description;
        this.pluginId = pluginId;
    }
}
