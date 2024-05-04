package com.victor.kochnev.core.dto.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.core.dto.domain.value.object.PluginDescriptionTaskDto;
import com.victor.kochnev.domain.enums.TaskDecision;
import com.victor.kochnev.domain.enums.TaskType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    @JsonProperty("id")
    @NotNull
    private UUID id;
    @JsonProperty("createDate")
    @NotNull
    private ZonedDateTime createDate;
    @JsonProperty("description")
    @NotNull
    private PluginDescriptionTaskDto description;
    @JsonProperty("decision")
    @NotNull
    private TaskDecision decision;
    @JsonProperty("type")
    @NotNull
    private TaskType type;
    @JsonProperty("comment")
    private String comment;
    @JsonProperty("plugin")
    @NotNull
    private PluginInfoDto plugin;
}
