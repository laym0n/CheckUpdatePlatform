package com.victor.kochnev.dal.entity.converter;

import lombok.SneakyThrows;

import java.util.List;

public abstract class ListConverter<X> extends JsonConverter<List<X>> {

    @Override
    protected final Class<List<X>> getMappingClass() {
        return (Class<List<X>>) (Object) List.class;
    }

    protected abstract Class<X> getInnerClass();

    @SneakyThrows
    @Override
    public List<X> convertToEntityAttribute(String dbData) {
        if (dbData == null) {
            return null;
        }
        Class<X> innerClass = getInnerClass();
        return objectMapper.readValue(dbData, objectMapper.getTypeFactory().constructCollectionType(List.class, innerClass));
    }
}
