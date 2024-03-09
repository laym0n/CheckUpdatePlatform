package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.WebResource;
import com.victor.kochnev.domain.enums.ObserveStatus;

import java.util.Optional;
import java.util.UUID;

public interface WebResourceRepository {
    WebResource create(WebResource webResource);

    Optional<WebResource> findByNameAndPluginId(String name, UUID pluginId);

    WebResource findById(UUID webResourcesId);

    WebResource update(WebResource webResource);

    WebResource setStatus(ObserveStatus status, UUID webResourceId);
}
