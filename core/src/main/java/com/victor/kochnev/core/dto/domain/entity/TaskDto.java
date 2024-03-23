package com.victor.kochnev.core.dto.domain.entity;

import com.victor.kochnev.core.dto.domain.value.object.PluginDescriptionDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private UUID id;
    private PluginDescriptionDto description;
    private PluginDto plugin;
}
