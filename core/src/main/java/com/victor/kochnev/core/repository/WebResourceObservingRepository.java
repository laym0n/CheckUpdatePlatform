package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.WebResourceObserving;

import java.util.Optional;
import java.util.UUID;

public interface WebResourceObservingRepository {
    WebResourceObserving create(WebResourceObserving webResourceObserving);

    Optional<WebResourceObserving> findByWebResourceIdAndUserId(UUID webResourceId, UUID userId);
}
