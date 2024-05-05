package com.victor.kochnev.core.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserRegistrationRequestDto {
    @JsonProperty("login")
    @NotBlank
    private String login;
    @JsonProperty("email")
    @Email
    @NotBlank
    private String email;
    @JsonProperty("password")
    @NotBlank
    private String password;
}
