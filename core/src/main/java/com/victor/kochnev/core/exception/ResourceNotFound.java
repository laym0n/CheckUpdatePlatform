package com.victor.kochnev.core.exception;


import com.victor.kochnev.domain.entity.BaseEntity;

public class ResourceNotFound extends CoreException {
    public ResourceNotFound(String resource, String value, String field) {
        super(resource + " with " + value + " in " + field + "does not exist");
    }

    public static ResourceNotFound create(Class<? extends BaseEntity> clazz, String value, String field) {
        return new ResourceNotFound(clazz.getCanonicalName(), value, "id");
    }
}
