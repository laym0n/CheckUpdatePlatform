package com.victor.kochnev.core.security.entity;

import com.victor.kochnev.core.converter.Default;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UserSecurity extends User {
    private UUID id;

    @Default
    public UserSecurity(UUID id, String email, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities) {
        super(email, password, enabled, true, true, true, authorities);
        this.id = id;
    }

    public UserSecurity(UUID id, String username) {
        super(username, "", List.of());
        this.id = id;
    }
}
