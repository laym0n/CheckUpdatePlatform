package com.victor.kochnev.dal.entity.converter;

public class StringListConverter extends ListConverter<String> {
    @Override
    protected Class<String> getInnerClass() {
        return String.class;
    }
}
