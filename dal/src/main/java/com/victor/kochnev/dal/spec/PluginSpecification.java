package com.victor.kochnev.dal.spec;

import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescription_;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.dal.entity.PluginEntity_;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
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

            // Подсчитываем количество совпадающих тегов
            Expression<Integer> matchingTagsCount = cb.function(
                    "jsonb_array_length",
                    Integer.class,
                    cb.function(
                            "array_agg",
                            Object.class,
                            cb.function(
                                    "jsonb_array_elements_text",
                                    String.class,
                                    jsonbTags
                            )
                    )
            );

            // Создаем предикат для сравнения тегов
            Predicate matchingTagsPredicate = cb.greaterThan(
                    matchingTagsCount,
                    (int) tags.stream().map(cb::literal).count()
            );

            // Возвращаем спецификацию, отсортированную по убыванию количества совпадающих тегов
            query.orderBy(cb.desc(matchingTagsCount));
            return matchingTagsPredicate;
        };
    }
}
