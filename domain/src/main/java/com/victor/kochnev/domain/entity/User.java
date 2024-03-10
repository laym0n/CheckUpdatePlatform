package com.victor.kochnev.domain.entity;

import com.victor.kochnev.domain.enums.UserRole;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.Collection;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class User extends BaseEntity {
    private String email;
    private String telegramInfo;
    private String password;
    @Builder.Default
    private boolean enabled = true;
    /**
     * Роли пользователя
     */
    @Builder.Default
    private Collection<UserRole> rolesCollection = new ArrayList<>();

    public void addRole(UserRole userRole) {
        getRolesCollection().add(userRole);
    }
}
