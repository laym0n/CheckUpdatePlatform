package com.victor.kochnev.dal.spec;

import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.domain.enums.ObserveStatus;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Path;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public final class WebResourceObservingSpecification {
    private WebResourceObservingSpecification() {
    }

    public static Specification<WebResourceObservingEntity> byUserId(UUID id) {
        return (root, query, cb) -> cb.equal(root.get(WebResourceObservingEntity_.user).get(UserEntity_.id), id);
    }

    public static Specification<WebResourceObservingEntity> byWebResourceId(UUID id) {
        return (root, query, cb) -> cb.equal(root.get(WebResourceObservingEntity_.webResource).get(WebResourceEntity_.id), id);
    }

    public static Specification<WebResourceObservingEntity> byWebResourceName(String name) {
        return (root, query, cb) -> cb.equal(root.get(WebResourceObservingEntity_.webResource).get(WebResourceEntity_.name), name);
    }

    public static Specification<WebResourceObservingEntity> getAllObservers() {
        return (root, query, cb) -> {
            root.join(WebResourceObservingEntity_.webResource);
            root.join(WebResourceObservingEntity_.user);
            return cb.conjunction();
        };
    }

    public static Specification<WebResourceObservingEntity> byExpiredDateNullOrAfter(ZonedDateTime expiredDate) {
        return (root, query, cb) -> {
            ListJoin<PluginEntity, PluginUsageEntity> pluginUsageJoin = root
                    .join(WebResourceObservingEntity_.webResource)
                    .join(WebResourceEntity_.plugin)
                    .join(PluginEntity_.pluginUsageEntityList);
            Path<UUID> pluginUsageUserId = pluginUsageJoin
                    .get(PluginUsageEntity_.user)
                    .get(UserEntity_.id);
            Path<UUID> observingUserId = root.get(WebResourceObservingEntity_.user).get(UserEntity_.id);
            Path<ZonedDateTime> zonedDateTimePath = pluginUsageJoin
                    .get(PluginUsageEntity_.expiredDate);
            return cb.and(cb.equal(pluginUsageUserId, observingUserId), cb.or(zonedDateTimePath.isNull(), cb.greaterThanOrEqualTo(zonedDateTimePath, expiredDate)));
        };
    }

    public static Specification<WebResourceObservingEntity> byStatus(ObserveStatus status) {
        return (root, query, cb) -> cb.equal(root.get(WebResourceObservingEntity_.status), status);
    }

    public static Specification<WebResourceObservingEntity> byPluginIds(List<UUID> pluginIds) {
        return (root, query, cb) -> root.get(WebResourceObservingEntity_.webResource).get(WebResourceEntity_.plugin).get(BaseDalEntity_.id).in(pluginIds);
    }

    public static Specification<WebResourceObservingEntity> byUserIds(List<UUID> userIds) {
        return (root, query, cb) -> root.get(WebResourceObservingEntity_.user).get(BaseDalEntity_.id).in(userIds);
    }
}
