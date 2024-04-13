package com.victor.kochnev.rest.presenters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRequest {
    @JsonProperty("email")
    @Email
    @NotNull
    private String email;
    @JsonProperty("password")
    @NotNull
    private String password;
    @JsonProperty("rememberMe")
    private Boolean rememberMe;
}

