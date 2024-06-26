package com.victor.kochnev.core.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPluginRequestDto {
    @JsonProperty("name")
    @NotNull
    private String name;
    @JsonProperty("baseUrl")
    @NotNull
    private String baseUrl;
}
