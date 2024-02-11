package com.victor.kochnev.core.service.plugin;

import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.domain.entity.Plugin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PluginServiceImpl implements PluginService {
    private final PluginRepository pluginRepository;

    @Override
    public Plugin findById(UUID id) {
        return pluginRepository.findById(id);
    }
}
