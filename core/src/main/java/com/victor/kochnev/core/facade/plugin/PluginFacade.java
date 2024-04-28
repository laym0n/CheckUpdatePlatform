package com.victor.kochnev.core.facade.plugin;

import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginsRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import com.victor.kochnev.core.dto.response.GetPluginsResponseDto;

public interface PluginFacade {

    AddPluginResponseDto addPlugin(AddPluginRequestDto requestDto);

    GetPluginsResponseDto getOwnPlugins(GetPluginsRequestDto request);

    GetPluginsResponseDto getPlugins(GetPluginsRequestDto request);

    GetPluginsResponseDto getPluginsForCurrentUser(GetPluginsRequestDto request);
}
