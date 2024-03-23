package com.victor.kochnev.dal.entity.converter;

import com.victor.kochnev.domain.enums.UserRole;

public class UserRoleListConverter extends ListConverter<UserRole> {
    @Override
    protected Class<UserRole> getInnerClass() {
        return UserRole.class;
    }
}
