package com.victor.kochnev.core.service.plugin;

import com.victor.kochnev.core.converter.DomainPluginMapper;
import com.victor.kochnev.core.converter.RequestDtoMapper;
import com.victor.kochnev.core.dto.domain.entity.PluginInfoDto;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginsRequestDto;
import com.victor.kochnev.core.dto.request.UpdatePluginRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import com.victor.kochnev.core.dto.response.GetPluginsResponseDto;
import com.victor.kochnev.core.dto.response.RefreshTokenResponseDto;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.core.service.pluginusage.PluginUsageService;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.enums.PluginStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PluginServiceImpl implements PluginService {
    private final PluginUsageService pluginUsageService;
    private final PluginRepository pluginRepository;
    private final UserRepository userRepository;
    private final DomainPluginMapper pluginMapper;
    private final RequestDtoMapper requestDtoMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public Plugin getById(UUID id) {
        return pluginRepository.getById(id);
    }

    @Override
    @Transactional
    public Plugin findByName(String name) {
        return pluginRepository.findByName(name)
                .orElseThrow(() -> ResourceNotFoundException.create(Plugin.class, name, "accessToken"));
    }

    @Override
    @Transactional
    public AddPluginResponseDto create(UUID userId, AddPluginRequestDto requestDto) {
        User ownerUser = userRepository.getById(userId);
        Plugin newPlugin = pluginMapper.mapToDomain(requestDto);
        newPlugin.setOwnerUser(ownerUser);
        newPlugin.setStatus(PluginStatus.CREATED);
        String accessToken = UUID.randomUUID().toString();
        newPlugin.setAccessToken(passwordEncoder.encode(accessToken));
        Plugin createdPlugin = pluginRepository.create(newPlugin);

        pluginUsageService.createOwningUsage(userId, createdPlugin.getId());

        AddPluginResponseDto responseDto = pluginMapper.mapToAddPluginResponseDto(createdPlugin);
        responseDto.setAccessToken(accessToken);
        return responseDto;
    }

    @Override
    @Transactional
    public RefreshTokenResponseDto refreshAccessToken(UUID pluginId) {
        Plugin plugin = pluginRepository.getById(pluginId);

        String accessToken = UUID.randomUUID().toString();
        plugin.setAccessToken(passwordEncoder.encode(accessToken));
        pluginRepository.update(plugin);

        var response = new RefreshTokenResponseDto();
        response.setAccessToken(accessToken);
        return response;
    }

    @Override
    @Transactional
    public Plugin update(Plugin plugin) {
        return pluginRepository.update(plugin);
    }

    @Override
    @Transactional
    public PluginInfoDto update(UpdatePluginRequestDto requestDto) {
        Plugin plugin = pluginRepository.getById(requestDto.getPluginId());
        pluginMapper.update(plugin, requestDto);
        plugin = pluginRepository.update(plugin);
        return pluginMapper.mapToDto(plugin);
    }

    @Override
    @Transactional
    public GetPluginsResponseDto getPlugins(GetPluginsRequestDto request) {
        var dalRequest = requestDtoMapper.mapToDal(request);
        dalRequest.getFilters().setStatuses(List.of(PluginStatus.ACTIVE));
        var dalResponseDto = pluginRepository.getByFilters(dalRequest);
        return pluginMapper.mapToDto(dalResponseDto);
    }

    @Override
    public GetPluginsResponseDto getPluginsForCurrentUser(GetPluginsRequestDto request, UUID userId) {
        var dalRequest = requestDtoMapper.mapToDal(request);
        dalRequest.getFilters().setPluginUsageUserId(userId);
        var dalResponseDto = pluginRepository.getByFilters(dalRequest);
        return pluginMapper.mapToDto(dalResponseDto);
    }

    @Override
    public GetPluginsResponseDto getOwnPlugins(GetPluginsRequestDto request, UUID userId) {
        var dalRequest = requestDtoMapper.mapToDal(request);
        dalRequest.getFilters().setOwnerIds(List.of(userId));
        var dalResponseDto = pluginRepository.getByFilters(dalRequest);
        return pluginMapper.mapToDto(dalResponseDto);
    }
}
