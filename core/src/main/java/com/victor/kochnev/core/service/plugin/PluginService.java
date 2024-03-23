package com.victor.kochnev.core.service.plugin;

import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.value.object.PluginDescription;

import java.util.UUID;

public interface PluginService {
    Plugin getById(UUID id);

    Plugin findByName(String name);

    AddPluginResponseDto create(UUID userId, AddPluginRequestDto requestDto);

    Plugin updateDescription(UUID id, PluginDescription description);
}
