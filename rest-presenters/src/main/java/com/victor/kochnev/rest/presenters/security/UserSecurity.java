package com.victor.kochnev.rest.presenters.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

public class UserSecurity extends User {
    public UserSecurity(String email, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, enabled, true, true, true, authorities);
    }
}
