package com.victor.kochnev.core.dto.request;

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
public class CreateTaskRequestDto {
    private UUID pluginId;
    private PluginDescriptionDto description;
}