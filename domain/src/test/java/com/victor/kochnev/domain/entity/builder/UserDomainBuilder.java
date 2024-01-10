package com.victor.kochnev.domain.entity.builder;

import com.victor.kochnev.domain.entity.User;

import java.time.ZonedDateTime;
import java.util.UUID;

public class UserDomainBuilder {
    public static final String DEFAULT_EMAIL = "victor_k02@mail.ru";
    public static final String DEFAULT_PASSWORD = "password";

    private UserDomainBuilder() {
    }

    public static User.UserBuilder<?, ?> defaultUser() {
        return User.builder()
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_PASSWORD);
    }

    public static User.UserBuilder<?, ?> persistedDefaultUser() {
        return defaultUser()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
