package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.TaskRepository;
import com.victor.kochnev.dal.repository.jpa.TaskEntityRepository;
import com.victor.kochnev.domain.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final TaskEntityRepository taskRepository;

    @Override
    public Task create(Task task) {
        return null;
    }
}
