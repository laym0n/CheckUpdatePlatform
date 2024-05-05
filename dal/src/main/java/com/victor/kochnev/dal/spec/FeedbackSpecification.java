package com.victor.kochnev.dal.spec;

import com.victor.kochnev.dal.entity.BaseDalEntity_;
import com.victor.kochnev.dal.entity.FeedbackEntity;
import com.victor.kochnev.dal.entity.FeedbackEntity_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public final class FeedbackSpecification {
    private FeedbackSpecification() {
    }

    public static Specification<FeedbackEntity> getAll() {
        return null;
    }

    public static Specification<FeedbackEntity> byIds(List<UUID> ids) {
        return (root, query, cb) -> root.get(BaseDalEntity_.id).in(ids);
    }

    public static Specification<FeedbackEntity> byPluginIds(List<UUID> pluginIds) {
        return (root, query, cb) -> root.get(FeedbackEntity_.plugin).get(BaseDalEntity_.id).in(pluginIds);
    }

    public static Specification<FeedbackEntity> byUserIds(List<UUID> userIds) {
        return (root, query, cb) -> root.get(FeedbackEntity_.user).get(BaseDalEntity_.id).in(userIds);
    }

    public static Specification<FeedbackEntity> byExcludedUserIds(List<UUID> excludedUserIds) {
        return Specification.not(byUserIds(excludedUserIds));
    }
}
