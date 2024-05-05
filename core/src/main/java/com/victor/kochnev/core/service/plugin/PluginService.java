package com.victor.kochnev.core.service.plugin;

import com.victor.kochnev.core.dto.domain.entity.PluginInfoDto;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginsRequestDto;
import com.victor.kochnev.core.dto.request.UpdatePluginRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import com.victor.kochnev.core.dto.response.GetPluginsResponseDto;
import com.victor.kochnev.core.dto.response.RefreshTokenResponseDto;
import com.victor.kochnev.domain.entity.Plugin;

import java.util.UUID;

public interface PluginService {
    Plugin getById(UUID id);

    Plugin findByName(String name);

    AddPluginResponseDto create(UUID userId, AddPluginRequestDto requestDto);

    Plugin update(Plugin plugin);

    GetPluginsResponseDto getPlugins(GetPluginsRequestDto request);

    GetPluginsResponseDto getPluginsForCurrentUser(GetPluginsRequestDto request, UUID userId);

    GetPluginsResponseDto getOwnPlugins(GetPluginsRequestDto request, UUID userId);

    RefreshTokenResponseDto refreshAccessToken(UUID pluginId);

    PluginInfoDto update(UpdatePluginRequestDto requestDto);
}
