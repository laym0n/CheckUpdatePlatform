package com.victor.kochnev.dal.entity.builder;

import com.victor.kochnev.dal.entity.UserEntity;

import java.time.ZonedDateTime;
import java.util.UUID;

public class UserEntityBuilder {
    public static final String DEFAULT_EMAIL = "victor_k02@mail.ru";
    public static final String DEFAULT_PASSWORD = "password";

    private UserEntityBuilder() {
    }

    public static UserEntity.UserEntityBuilder<?, ?> defaultEntityUser() {
        return UserEntity.builder()
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_PASSWORD);
    }

    public static UserEntity.UserEntityBuilder<?, ?> persistedDefaultEntityUser() {
        return defaultEntityUser()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
