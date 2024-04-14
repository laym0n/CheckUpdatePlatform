package com.victor.kochnev.rest.presenters.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.core.dto.domain.entity.UserInfoDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticateResponse {
    @JsonProperty("jwtToken")
    @NotNull
    private JwtTokenDto jwtToken;
    @JsonProperty("user")
    @NotNull
    private UserInfoDto user;
}

