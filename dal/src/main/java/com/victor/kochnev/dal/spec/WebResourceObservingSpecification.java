package com.victor.kochnev.dal.spec;

import com.victor.kochnev.dal.entity.*;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.UUID;

public final class WebResourceObservingSpecification {
    private WebResourceObservingSpecification() {
    }

    public static Specification<WebResourceObservingEntity> withUserIdPluginIdAndExpiredDateNullOrAfter(UUID webResourceId, ZonedDateTime expiredDate) {
        Specification<WebResourceObservingEntity> spec = Specification.where(getAllObservers());
        spec = spec.and(byWebResourceId(webResourceId));
        spec = spec.and(byExpiredDateNullOrAfter(webResourceId, expiredDate));
        return spec;
    }

    private static Specification<WebResourceObservingEntity> byExpiredDateNullOrAfter(UUID webResourceId, ZonedDateTime expiredDate) {
        return (root, query, cb) -> {
            Specification<PluginUsageEntity> pluginUsageSpecification = PluginUsageSpecification.expiredDateNullOrAfter(expiredDate);
            pluginUsageSpecification = pluginUsageSpecification.and(PluginUsageSpecification.byWebResourceId(webResourceId));
            return pluginUsageSpecification.toPredicate(query.from(PluginUsageEntity.class), query, cb);
        };
    }

    private static Specification<WebResourceObservingEntity> byUserId(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get(WebResourceObservingEntity_.user).get(UserEntity_.id), userId);
    }

    private static Specification<WebResourceObservingEntity> byWebResourceId(UUID pluginId) {
        return (root, query, cb) -> cb.equal(root.get(WebResourceObservingEntity_.webResource).get(WebResourceEntity_.id), pluginId);
    }

    private static Specification<WebResourceObservingEntity> getAllObservers() {
        return (root, query, cb) -> {
            root.join(WebResourceObservingEntity_.webResource);
            root.join(WebResourceObservingEntity_.user);
            return cb.conjunction();
        };
    }
}
