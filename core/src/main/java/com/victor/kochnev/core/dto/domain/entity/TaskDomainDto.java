package com.victor.kochnev.core.dto.domain.entity;

import com.victor.kochnev.domain.enums.TaskDecision;
import com.victor.kochnev.domain.value.object.PluginDescription;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDomainDto {
    private UUID id;
    private PluginDescription description;
    private TaskDecision decision;
    private String comment;
    private PluginDomainDto plugin;
}
