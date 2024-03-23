package com.victor.kochnev.dal.entity;

import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescriptionBuilder;
import com.victor.kochnev.domain.enums.ObserveStatus;
import com.victor.kochnev.domain.enums.TaskType;

import java.time.ZonedDateTime;
import java.util.UUID;

public class TaskEntityBuilder {
    public static final ObserveStatus DEFAULT_STATUS = ObserveStatus.OBSERVE;

    private TaskEntityBuilder() {
    }

    public static TaskEntity.TaskEntityBuilder<?, ?> defaultBuilder() {
        return TaskEntity.builder()
                .type(TaskType.INITIALIZE)
                .decision(null)
                .comment(null)
                .description(EmbeddablePluginDescriptionBuilder.defaultBuilder().build());
    }

    public static TaskEntity.TaskEntityBuilder<?, ?> persistedDefaultBuilder() {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }

    public static TaskEntity.TaskEntityBuilder<?, ?> persistedPostfixBuilder(int postfix) {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
