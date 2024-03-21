package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.Task;

public interface TaskRepository {
    Task create(Task task);
}
