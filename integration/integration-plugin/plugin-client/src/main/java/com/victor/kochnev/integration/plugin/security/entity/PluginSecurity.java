package com.victor.kochnev.integration.plugin.security.entity;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.UUID;

@Data
public class PluginSecurity extends User {
    private UUID id;

    public PluginSecurity(UUID id, String name, String accessToken, Collection<? extends GrantedAuthority> authorities) {
        super(name, accessToken, true, true, true, true, authorities);
        this.id = id;
    }
}
