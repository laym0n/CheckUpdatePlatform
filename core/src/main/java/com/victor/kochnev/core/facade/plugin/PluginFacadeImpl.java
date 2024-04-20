package com.victor.kochnev.core.facade.plugin;

import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginsRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import com.victor.kochnev.core.dto.response.GetPluginsResponseDto;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.core.service.plugin.PluginService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PluginFacadeImpl implements PluginFacade {
    private final SecurityUserService securityUserService;
    private final PluginService pluginService;

    @Override
    public AddPluginResponseDto addPlugin(AddPluginRequestDto requestDto) {
        UserSecurity user = securityUserService.getCurrentUser();
        UUID userId = user.getId();
        return pluginService.create(userId, requestDto);
    }

    @Override
    public GetPluginsResponseDto getPlugins(GetPluginsRequestDto request) {
        return pluginService.getPlugins(request);
    }

    @Override
    public GetPluginsResponseDto getPluginsForCurrentUser(GetPluginsRequestDto request) {
        UserSecurity currentUser = securityUserService.getCurrentUser();
        return pluginService.getPluginsForCurrentUser(request, currentUser.getId());
    }
}
