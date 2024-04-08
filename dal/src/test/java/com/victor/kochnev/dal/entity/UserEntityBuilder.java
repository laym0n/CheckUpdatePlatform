package com.victor.kochnev.dal.entity;

import com.victor.kochnev.domain.enums.UserRole;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class UserEntityBuilder {
    public static final String DEFAULT_EMAIL = "victor_k02@mail.ru";
    public static final String DEFAULT_PASSWORD = "$2a$10$LgKxgwMdKMJFEFF8YVeJVu0JK7cwuv/D1PBRxRcACf3XTsWx9g5mi";
    public static final List<UserRole> DEFAULT_ROLES = List.of(UserRole.SIMPLE_USER);

    private UserEntityBuilder() {
    }

    public static UserEntity.UserEntityBuilder<?, ?> defaultBuilder() {
        return UserEntity.builder()
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_PASSWORD)
                .roles(DEFAULT_ROLES)
                .enabled(true);
    }

    public static UserEntity.UserEntityBuilder<?, ?> persistedDefaultBuilder() {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }

    public static UserEntity.UserEntityBuilder<?, ?> postfixBuilder(int postfix) {
        return UserEntity.builder()
                .email(DEFAULT_EMAIL + postfix)
                .password(DEFAULT_PASSWORD)
                .roles(List.of(UserRole.SIMPLE_USER))
                .enabled(true);
    }

    public static UserEntity.UserEntityBuilder<?, ?> persistedPostfixBuilder(int postfix) {
        return postfixBuilder(postfix)
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
