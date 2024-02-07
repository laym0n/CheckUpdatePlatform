package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.domain.entity.Plugin;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class PluginRepositoryImpl implements PluginRepository {
    @Override
    public Plugin findById(UUID id) {
        return null;
    }
}
