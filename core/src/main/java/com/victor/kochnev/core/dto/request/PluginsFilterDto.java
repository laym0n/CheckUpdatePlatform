package com.victor.kochnev.core.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PluginsFilterDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("tags")
    private List<String> tags;
}
