package com.victor.kochnev.dal.spec;

import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescription_;
import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.domain.enums.PluginStatus;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public final class PluginSpecification {
    private PluginSpecification() {
    }

    public static Specification<PluginEntity> byName(String name) {
        return (root, query, cb) -> cb.like(cb.lower(root.get(PluginEntity_.name)), "%" + name.toLowerCase() + "%");
    }

    public static Specification<PluginEntity> getAll() {
        return (root, query, cb) -> {
            root.join(PluginEntity_.ownerUser);
            return cb.conjunction();
        };
    }

    public static Specification<PluginEntity> byTags(List<String> tags) {
        return (root, query, cb) -> {
            var jsonbTags = root.get(PluginEntity_.description)
                    .get(EmbeddablePluginDescription_.specificDescription)
                    .get("tags");

            Expression<String[]> tagsArrayExpression = cb.function("jsonb_extract_path_text", String[].class, jsonbTags, cb.literal("tags"));
            Expression<String> jsonTagsArrayExpression = cb.function("JSONB", String.class, tagsArrayExpression);
            Expression<String> arrayTags = cb.function("ARRAY", String.class, tags.stream().map(cb::literal).toArray(Expression[]::new));
            Expression<Long> tagsExpression = cb.function("count_matching_in_array", Long.class, jsonTagsArrayExpression, arrayTags);

            query.orderBy(cb.desc(tagsExpression));

            return null;
        };
    }

    public static Specification<PluginEntity> byIds(List<UUID> ids) {
        return (root, query, cb) -> root.get(BaseDalEntity_.id).in(ids);
    }

    public static Specification<PluginEntity> byPluginUsageUserId(UUID userId) {
        return (root, query, cb) -> cb.equal(root.join(PluginEntity_.pluginUsageEntityList).get(PluginUsageEntity_.user).get(UserEntity_.id), userId);
    }

    public static Specification<PluginEntity> byStatuses(List<PluginStatus> statuses) {
        return (root, query, cb) -> root.get(PluginEntity_.status).in(statuses);
    }

    public static Specification<PluginEntity> byOwnerIds(List<UUID> ownerIds) {
        return (root, query, cb) -> root.get(PluginEntity_.ownerUser).get(BaseDalEntity_.id).in(ownerIds);
    }
}
