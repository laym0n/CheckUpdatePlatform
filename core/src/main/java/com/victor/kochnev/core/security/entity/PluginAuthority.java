package com.victor.kochnev.core.security.entity;

import org.springframework.security.core.GrantedAuthority;

public enum PluginAuthority implements GrantedAuthority {
    PLUGIN;

    @Override
    public String getAuthority() {
        return "ROLE_" + this.name();
    }
}
