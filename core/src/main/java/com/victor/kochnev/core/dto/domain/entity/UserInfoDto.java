package com.victor.kochnev.core.dto.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.domain.enums.UserRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto {
    @JsonProperty("id")
    @NotNull
    private UUID id;
    @JsonProperty("email")
    @Email
    @NotNull
    private String email;
    @JsonProperty("login")
    @Email
    @NotNull
    private String login;
    @JsonProperty("roles")
    @NotNull
    private List<UserRole> roles;
}

