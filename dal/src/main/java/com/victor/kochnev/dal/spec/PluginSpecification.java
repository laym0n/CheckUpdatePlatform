package com.victor.kochnev.dal.spec;

import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescription_;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.dal.entity.PluginEntity_;
import jakarta.persistence.criteria.Expression;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

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
}
