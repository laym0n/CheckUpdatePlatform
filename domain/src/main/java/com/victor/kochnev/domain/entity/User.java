package com.victor.kochnev.domain.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class User extends BaseEntity {
    private String email;
    private String password;
    @Builder.Default
    private boolean enabled = true;
}
