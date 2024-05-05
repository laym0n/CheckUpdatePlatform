package com.victor.kochnev.core.facade.plugin;

import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginsRequestDto;
import com.victor.kochnev.core.dto.request.UpdatePluginRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import com.victor.kochnev.core.dto.response.GetPluginsResponseDto;
import com.victor.kochnev.core.dto.response.RefreshTokenResponseDto;

import java.util.UUID;

public interface PluginFacade {

    AddPluginResponseDto addPlugin(AddPluginRequestDto requestDto);

    RefreshTokenResponseDto refreshAccessToken(UUID pluginId);

    GetPluginsResponseDto getOwnPlugins(GetPluginsRequestDto request);

    PluginDto updatePlugin(UpdatePluginRequestDto requestDto);

    GetPluginsResponseDto getPlugins(GetPluginsRequestDto request);

    GetPluginsResponseDto getPluginsForCurrentUser(GetPluginsRequestDto request);
}
