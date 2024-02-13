package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.WebResourceObservingRepository;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WebResourceObservingRepositoryImpl implements WebResourceObservingRepository {
    @Override
    public WebResourceObserving create(WebResourceObserving webResourceObserving) {
        return null;
    }

    @Override
    public Optional<WebResourceObserving> findByWebResourceIdAndUserId(UUID webResourceId, UUID userId) {
        return Optional.empty();
    }
}
