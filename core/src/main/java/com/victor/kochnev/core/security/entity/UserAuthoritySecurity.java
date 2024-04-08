package com.victor.kochnev.core.security.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthoritySecurity implements GrantedAuthority {
    private String authority;
}
