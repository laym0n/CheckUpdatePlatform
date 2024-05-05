package com.victor.kochnev.dal.entity;

import java.time.ZonedDateTime;
import java.util.UUID;

public class FeedbackEntityBuilder {
    public final static String DEFAULT_COMMENT = "comment";
    public final static Integer DEFAULT_RATING = 5;

    private FeedbackEntityBuilder() {
    }

    public static FeedbackEntity.FeedbackEntityBuilder<?, ?> defaultBuilder() {
        return FeedbackEntity.builder()
                .comment(DEFAULT_COMMENT)
                .rating(DEFAULT_RATING);
    }

    public static FeedbackEntity.FeedbackEntityBuilder<?, ?> persistedDefaultBuilder() {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }

    public static FeedbackEntity.FeedbackEntityBuilder<?, ?> persistedBuilder(int postfix) {
        return FeedbackEntity.builder()
                .comment(DEFAULT_COMMENT)
                .rating(DEFAULT_RATING);
    }

    public static FeedbackEntity.FeedbackEntityBuilder<?, ?> persistedPostfixBuilder(int postfix) {
        return persistedBuilder(postfix)
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
