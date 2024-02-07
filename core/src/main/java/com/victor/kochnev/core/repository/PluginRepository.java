package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.Plugin;

import java.util.UUID;

public interface PluginRepository {
    Plugin findById(UUID id);
}
