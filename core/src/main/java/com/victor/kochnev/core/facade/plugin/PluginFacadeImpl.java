package com.victor.kochnev.core.facade.plugin;

import com.victor.kochnev.core.dto.domain.entity.PluginInfoDto;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginsRequestDto;
import com.victor.kochnev.core.dto.request.UpdatePluginRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import com.victor.kochnev.core.dto.response.GetPluginsResponseDto;
import com.victor.kochnev.core.dto.response.RefreshTokenResponseDto;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.core.service.plugin.PluginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
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
    @PreAuthorize("@authorizationService.verifyAuthenticatedUserCanManagePlugin(#pluginId)")
    public RefreshTokenResponseDto refreshAccessToken(@P("pluginId") UUID pluginId) {
        return pluginService.refreshAccessToken(pluginId);
    }

    @Override
    public GetPluginsResponseDto getOwnPlugins(GetPluginsRequestDto request) {
        UserSecurity currentUser = securityUserService.getCurrentUser();
        return pluginService.getOwnPlugins(request, currentUser.getId());
    }

    @Override
    @PreAuthorize("@authorizationService.verifyAuthenticatedUserCanManagePlugin(#requestDto.getPluginId())")
    public PluginInfoDto updatePlugin(@P("requestDto") UpdatePluginRequestDto requestDto) {
        return pluginService.update(requestDto);
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
