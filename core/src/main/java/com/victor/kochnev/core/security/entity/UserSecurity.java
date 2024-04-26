package com.victor.kochnev.core.security.entity;

import com.victor.kochnev.core.converter.Default;
import com.victor.kochnev.domain.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@ToString
public class UserSecurity extends User {
    private UUID id;
    private List<UserRole> roles = new ArrayList<>();

    @Default
    public UserSecurity(UUID id, String email, String password, boolean enabled, Collection<? extends GrantedAuthority> authorities, List<UserRole> roles) {
        super(email, password, enabled, true, true, true, authorities);
        this.id = id;
        this.roles = roles;
    }

    public UserSecurity(UUID id, String username) {
        super(username, "", List.of());
        this.id = id;
    }
}
