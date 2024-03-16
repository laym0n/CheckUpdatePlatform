package com.victor.kochnev.core.service.plugin;

import com.victor.kochnev.domain.entity.Plugin;

import java.util.UUID;

public interface PluginService {
    Plugin getById(UUID id);

    Plugin findByName(String name);
}
