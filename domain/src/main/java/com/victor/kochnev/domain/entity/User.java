package com.victor.kochnev.domain.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {
    private String email;
    private String password;
    private boolean enabled = true;
}
