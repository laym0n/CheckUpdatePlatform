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
    private String password;
    @Builder.Default
    private boolean enabled = true;
    /**
     * Роли пользователя
     */
    private Collection<UserRole> rolesCollection;

    public Collection<UserRole> getRolesCollection() {
        if (rolesCollection == null) {
            rolesCollection = new ArrayList<>();
        }
        return rolesCollection;
    }

    public void addRole(UserRole userRole) {
        getRolesCollection().add(userRole);
    }
}
