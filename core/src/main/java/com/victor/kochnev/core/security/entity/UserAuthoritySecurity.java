package com.victor.kochnev.core.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
public class UserAuthoritySecurity implements GrantedAuthority {
    private String authority;
}
