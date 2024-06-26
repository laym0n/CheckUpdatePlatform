package com.victor.kochnev.dal.spec;

import com.victor.kochnev.dal.entity.*;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.ListJoin;
import org.springframework.data.jpa.domain.Specification;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public final class PluginUsageSpecification {
    private PluginUsageSpecification() {
    }

    public static Specification<PluginUsageEntity> withUserIdPluginIdAndExpiredDateNullOrAfter(UUID userId, UUID pluginId, ZonedDateTime expiredDate) {
        Specification<PluginUsageEntity> spec = Specification.where(getAll());
        spec = spec.and(byUserId(userId));
        spec = spec.and(byPluginId(pluginId));
        spec = spec.and(byExpiredDateAfter(expiredDate).or(byExpiredDateNull()));
        return spec;
    }

    public static Specification<PluginUsageEntity> expiredDateNullOrAfter(ZonedDateTime expiredDate) {
        Specification<PluginUsageEntity> spec = Specification.where(getAll());
        spec = spec.and(byExpiredDateAfter(expiredDate).or(byExpiredDateNull()));
        return spec;
    }

    public static Specification<PluginUsageEntity> byWebResourceId(UUID id) {
        return (root, query, cb) -> {
            Join<PluginUsageEntity, PluginEntity> pluginJoin = root.join(PluginUsageEntity_.plugin);
            ListJoin<PluginEntity, WebResourceEntity> webResourceJoin = pluginJoin.join(PluginEntity_.webResourcesList);
            return cb.equal(webResourceJoin.get(WebResourceEntity_.ID), id);
        };
    }

    public static Specification<PluginUsageEntity> byWebResourceName(String name) {
        return (root, query, cb) -> {
            Join<PluginUsageEntity, PluginEntity> pluginJoin = root.join(PluginUsageEntity_.plugin);
            ListJoin<PluginEntity, WebResourceEntity> webResourceJoin = pluginJoin.join(PluginEntity_.webResourcesList);
            return cb.equal(webResourceJoin.get(WebResourceEntity_.name), name);
        };
    }

    private static Specification<PluginUsageEntity> byExpiredDateAfter(ZonedDateTime expiredDate) {
        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get(PluginUsageEntity_.EXPIRED_DATE), expiredDate);
    }

    private static Specification<PluginUsageEntity> byExpiredDateNull() {
        return (root, query, cb) -> cb.isNull(root.get(PluginUsageEntity_.EXPIRED_DATE));
    }

    public static Specification<PluginUsageEntity> byUserId(UUID userId) {
        return (root, query, cb) -> cb.equal(root.get(PluginUsageEntity_.user).get(BaseDalEntity_.id), userId);
    }

    public static Specification<PluginUsageEntity> byPluginId(UUID pluginId) {
        return (root, query, cb) -> cb.equal(root.get(PluginUsageEntity_.plugin).get(BaseDalEntity_.id), pluginId);
    }

    public static Specification<PluginUsageEntity> getAll() {
        return (root, query, cb) -> {
            root.join(PluginUsageEntity_.plugin);
            root.join(PluginUsageEntity_.user);
            return cb.conjunction();
        };
    }

    public static Specification<PluginUsageEntity> byPluginIds(List<UUID> pluginIds) {
        return (root, query, cb) -> root.get(PluginUsageEntity_.plugin).get(BaseDalEntity_.id).in(pluginIds);
    }

    public static Specification<PluginUsageEntity> byUserIds(List<UUID> userIds) {
        return (root, query, cb) -> root.get(PluginUsageEntity_.user).get(BaseDalEntity_.id).in(userIds);
    }
}
