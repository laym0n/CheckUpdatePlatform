package com.victor.kochnev.core.dto.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.domain.value.object.PluginDescription;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PluginInfoDto {
    @JsonProperty("id")
    @NotNull
    private UUID id;
    @JsonProperty("name")
    @NotNull
    private String name;
    @JsonProperty("description")
    @NotNull
    private PluginDescription description;
}
