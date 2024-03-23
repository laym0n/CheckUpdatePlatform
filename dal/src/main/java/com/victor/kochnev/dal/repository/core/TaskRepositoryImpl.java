package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.TaskRepository;
import com.victor.kochnev.dal.converter.EntityTaskMapper;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.dal.entity.TaskEntity;
import com.victor.kochnev.dal.repository.jpa.PluginEntityRepository;
import com.victor.kochnev.dal.repository.jpa.TaskEntityRepository;
import com.victor.kochnev.domain.entity.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TaskRepositoryImpl implements TaskRepository {
    private final TaskEntityRepository taskRepository;
    private final PluginEntityRepository pluginEntityRepository;
    private final EntityTaskMapper taskMapper;

    @Override
    public Task create(Task task) {
        TaskEntity taskEntity = taskMapper.mapToEntity(task);

        PluginEntity pluginEntity = pluginEntityRepository.findById(task.getPlugin().getId()).get();
        taskEntity.setPlugin(pluginEntity);

        taskEntity = taskRepository.save(taskEntity);
        return taskMapper.mapToDomain(taskEntity);
    }
}