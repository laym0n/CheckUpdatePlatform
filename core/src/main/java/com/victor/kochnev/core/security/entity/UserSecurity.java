package com.victor.kochnev.core.security.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UserSecurity extends User {
    private UUID id;

    public UserSecurity(UUID id, String email, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, enabled, true, true, true, authorities);
        this.id = id;
    }
}
