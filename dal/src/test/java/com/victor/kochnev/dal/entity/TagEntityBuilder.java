package com.victor.kochnev.dal.entity;

import java.time.ZonedDateTime;
import java.util.UUID;

public class TagEntityBuilder {
    public static final String DEFAULT_DATA = "tag";

    private TagEntityBuilder() {
    }

    public static TagEntity.TagEntityBuilder<?, ?> defaultBuilder() {
        return TagEntity.builder()
                .data(DEFAULT_DATA);
    }

    public static TagEntity.TagEntityBuilder<?, ?> persistedDefaultBuilder() {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }

    public static TagEntity.TagEntityBuilder<?, ?> postfixBuilder(int postfix) {
        return TagEntity.builder()
                .data(DEFAULT_DATA);
    }

    public static TagEntity.TagEntityBuilder<?, ?> persistedPostfixBuilder(int postfix) {
        return postfixBuilder(postfix)
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
