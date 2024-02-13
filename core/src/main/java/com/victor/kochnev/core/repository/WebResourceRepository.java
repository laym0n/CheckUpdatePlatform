package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.WebResource;

import java.util.Optional;
import java.util.UUID;

public interface WebResourceRepository {
    WebResource create(WebResource webResource);

    Optional<WebResource> findByNameAndPluginId(String name, UUID pluginId);

    WebResource findById(UUID webResourcesId);
}
