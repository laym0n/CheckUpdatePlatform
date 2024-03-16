package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.enums.ObserveStatus;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WebResourceObservingRepository {
    WebResourceObserving create(WebResourceObserving webResourceObserving);

    Optional<WebResourceObserving> findByWebResourceIdAndUserId(UUID webResourceId, UUID userId);

    List<WebResourceObserving> findAllWithExpiredDateAfterOrNull(String name, ZonedDateTime now);

    int countObserversWithStatus(UUID webResourceId, ObserveStatus observeStatus);

    WebResourceObserving update(WebResourceObserving webResourceObserving);

    WebResourceObserving getById(UUID observingId);
}
