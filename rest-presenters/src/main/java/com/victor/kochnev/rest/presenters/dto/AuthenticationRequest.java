package com.victor.kochnev.rest.presenters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @JsonProperty("login")
    @NotNull
    private String login;
    @JsonProperty("password")
    @NotNull
    private String password;
    @JsonProperty("rememberMe")
    private Boolean rememberMe;
}

