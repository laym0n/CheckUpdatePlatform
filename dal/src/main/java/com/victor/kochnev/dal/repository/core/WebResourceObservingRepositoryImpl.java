package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.WebResourceObservingRepository;
import com.victor.kochnev.dal.converter.EntityWebResourceObservingMapper;
import com.victor.kochnev.dal.entity.WebResourceObservingEntity;
import com.victor.kochnev.dal.repository.jpa.WebResourceObservingEntityRepository;
import com.victor.kochnev.dal.spec.WebResourceObservingSpecification;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.enums.ObserveStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class WebResourceObservingRepositoryImpl implements WebResourceObservingRepository {
    private final WebResourceObservingEntityRepository observingRepository;
    private final EntityWebResourceObservingMapper observingMapper;

    @Override
    public WebResourceObserving create(WebResourceObserving webResourceObserving) {
        WebResourceObservingEntity observingEntity = observingMapper.mapToEntity(webResourceObserving);
        observingRepository.save(observingEntity);
        return observingMapper.mapToDomain(observingEntity);
    }

    @Override
    public Optional<WebResourceObserving> findByWebResourceIdAndUserId(UUID webResourceId, UUID userId) {
        return observingRepository.findByWebResourceIdAndUserId(webResourceId, userId)
                .map(observingMapper::mapToDomain);
    }

    @Override
    public List<WebResourceObserving> findAllWithExpiredDateAfterOrNull(String name, ZonedDateTime now) {
        return null;
    }

    @Override
    public int countActualObserversWithStatus(UUID webResourceId, ObserveStatus observeStatus) {
        Specification<WebResourceObservingEntity> spec = WebResourceObservingSpecification.withUserIdPluginIdAndExpiredDateNullOrAfter(webResourceId, ZonedDateTime.now());
        return (int) observingRepository.count(spec);
    }

    @Override
    public WebResourceObserving update(WebResourceObserving webResourceObserving) {
        var observingEntity = getEntityById(webResourceObserving.getId());
        observingMapper.update(observingEntity, webResourceObserving);
        return observingMapper.mapToDomain(observingEntity);
    }

    @Override
    public WebResourceObserving getById(UUID observingId) {
        var observingEntity = getEntityById(observingId);
        return observingMapper.mapToDomain(observingEntity);
    }

    private WebResourceObservingEntity getEntityById(UUID id) {
        return observingRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create(WebResourceObserving.class, id.toString(), "id"));
    }
}
