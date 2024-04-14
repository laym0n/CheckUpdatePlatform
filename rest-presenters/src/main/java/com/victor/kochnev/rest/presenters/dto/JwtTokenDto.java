package com.victor.kochnev.rest.presenters.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Duration;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JwtTokenDto {
    @JsonProperty("accessToken")
    @NotNull
    private String accessToken;
    @JsonProperty("refreshToken")
    @NotNull
    private String refreshToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("accessTokenLiveDuration")
    @NotNull
    private Duration accessTokenLiveDuration;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    @JsonProperty("refreshTokenLiveDuration")
    @NotNull
    private Duration refreshTokenLiveDuration;
}

