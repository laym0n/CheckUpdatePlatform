package com.victor.kochnev.core.facade.plugin;

import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;

public interface PluginFacade {
    PluginDto addPlugin(AddPluginRequestDto requestDto);
}
