package com.victor.kochnev.domain.entity.builder;

import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.enums.UserRole;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserDomainBuilder {
    public static final String DEFAULT_EMAIL = "victor_k02@mail.ru";
    public static final String DEFAULT_PASSWORD = "password";

    private UserDomainBuilder() {
    }

    public static User.UserBuilder<?, ?> defaultUser() {
        List<UserRole> rolesList = new ArrayList<>();
        rolesList.add(UserRole.SIMPLE_USER);
        return User.builder()
                .email(DEFAULT_EMAIL)
                .password(DEFAULT_PASSWORD)
                .rolesCollection(rolesList)
                .enabled(true);
    }

    public static User.UserBuilder<?, ?> persistedDefaultUser() {
        return defaultUser()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
