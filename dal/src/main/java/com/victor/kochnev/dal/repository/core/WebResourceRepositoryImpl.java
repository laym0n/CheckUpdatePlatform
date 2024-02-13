package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.WebResourceRepository;
import com.victor.kochnev.domain.entity.WebResource;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WebResourceRepositoryImpl implements WebResourceRepository {
    @Override
    public WebResource create(WebResource webResource) {
        return null;
    }

    @Override
    public Optional<WebResource> findByNameAndPluginId(String name, UUID pluginId) {
        return Optional.empty();
    }

    @Override
    public WebResource findById(UUID webResourcesId) {
        return null;
    }
}
