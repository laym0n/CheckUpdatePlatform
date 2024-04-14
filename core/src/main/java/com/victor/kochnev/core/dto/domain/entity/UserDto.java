package com.victor.kochnev.core.dto.domain.entity;

import com.victor.kochnev.domain.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collection;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private UUID id;
    private String email;
    private String password;
    private Boolean enabled;
    private Collection<UserRole> roles;
}
