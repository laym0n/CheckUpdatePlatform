package com.victor.kochnev.core.exception;


import com.victor.kochnev.domain.entity.BaseEntity;

public class ResourceNotFoundException extends CoreException {
    public ResourceNotFoundException(String resource, String value, String field) {
        super(resource + " with " + value + " in " + field + " does not exist");
    }

    public static ResourceNotFoundException create(Class<? extends BaseEntity> clazz, String value, String field) {
        return new ResourceNotFoundException(clazz.getCanonicalName(), value, "id");
    }
}
