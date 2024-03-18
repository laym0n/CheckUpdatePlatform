package com.victor.kochnev.core.service.plugin;

import com.victor.kochnev.core.converter.DomainPluginMapper;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.enums.PluginStatus;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PluginServiceImpl implements PluginService {
    private final PluginRepository pluginRepository;
    private final UserRepository userRepository;
    private final DomainPluginMapper domainPluginMapper;
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
    public Plugin create(UUID userId, AddPluginRequestDto requestDto) {
        User ownerUser = userRepository.getById(userId);
        Plugin newPlugin = domainPluginMapper.mapToDomain(requestDto);
        newPlugin.setOwnerUser(ownerUser);
        newPlugin.setStatus(PluginStatus.CREATED);
        newPlugin.setAccessToken(passwordEncoder.encode(UUID.randomUUID().toString()));
        return pluginRepository.create(newPlugin);
    }
}
