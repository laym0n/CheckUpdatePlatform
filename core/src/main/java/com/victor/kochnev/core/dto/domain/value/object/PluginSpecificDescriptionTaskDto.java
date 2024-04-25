package com.victor.kochnev.core.dto.domain.value.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PluginSpecificDescriptionTaskDto {
    @JsonProperty("description")
    private String description;
    @JsonProperty("imagePaths")
    private List<String> imagePaths;
    @JsonProperty("tags")
    private List<TagInfoDto> tags;
}
