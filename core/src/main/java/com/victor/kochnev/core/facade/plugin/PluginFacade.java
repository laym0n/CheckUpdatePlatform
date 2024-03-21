package com.victor.kochnev.core.facade.plugin;

import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;

public interface PluginFacade {

    AddPluginResponseDto addPlugin(AddPluginRequestDto requestDto);
}
