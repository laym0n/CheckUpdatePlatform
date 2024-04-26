package com.victor.kochnev.dal.spec;

import com.victor.kochnev.dal.entity.BaseDalEntity_;
import com.victor.kochnev.dal.entity.PluginEntity_;
import com.victor.kochnev.dal.entity.TaskEntity;
import com.victor.kochnev.dal.entity.TaskEntity_;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public final class TaskSpecification {
    private TaskSpecification() {
    }

    public static Specification<TaskEntity> getAll() {
        return null;
    }

    public static Specification<TaskEntity> byIds(List<UUID> ids) {
        return (root, query, cb) -> root.get(BaseDalEntity_.id).in(ids);
    }

    public static Specification<TaskEntity> byOwnerIds(List<UUID> ownerIds) {
        return (root, query, cb) -> root.get(TaskEntity_.plugin).get(PluginEntity_.ownerUser).get(BaseDalEntity_.id).in(ownerIds);
    }
}
