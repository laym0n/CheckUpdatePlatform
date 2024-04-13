package com.victor.kochnev.core.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.core.dto.domain.entity.PluginDomainDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPluginResponseDto {
    @JsonProperty("plugin")
    @NotNull
    private PluginDomainDto plugin;
    @JsonProperty("accessToken")
    @NotNull
    private String accessToken;
}
