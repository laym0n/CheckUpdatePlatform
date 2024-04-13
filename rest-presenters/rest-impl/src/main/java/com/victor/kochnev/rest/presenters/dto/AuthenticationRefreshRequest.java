package com.victor.kochnev.rest.presenters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationRefreshRequest {
    @JsonProperty("refreshToken")
    @NotNull
    private String refreshToken;
    @JsonProperty("rememberMe")
    private Boolean rememberMe;
}

