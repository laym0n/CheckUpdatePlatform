package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.Task;

import java.util.UUID;

public interface TaskRepository {
    Task create(Task task);

    Task getById(UUID id);

    Task update(Task task);
}
