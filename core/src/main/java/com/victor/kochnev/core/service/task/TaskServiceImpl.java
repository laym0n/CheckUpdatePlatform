package com.victor.kochnev.core.service.task;

import com.victor.kochnev.core.converter.DomainTaskMapper;
import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.core.repository.PluginRepository;
import com.victor.kochnev.core.repository.TaskRepository;
import com.victor.kochnev.domain.entity.Plugin;
import com.victor.kochnev.domain.entity.Task;
import com.victor.kochnev.domain.enums.TaskType;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final PluginRepository pluginRepository;
    private final DomainTaskMapper taskMapper;

    @Override
    @Transactional
    public TaskDto create(CreateTaskRequestDto requestDto, TaskType type) {
        Task task = taskMapper.mapToDomain(requestDto);
        Plugin plugin = pluginRepository.getById(requestDto.getPluginId());
        task.setPlugin(plugin);
        task.setType(type);
        Task createdTask = taskRepository.create(task);
        return taskMapper.mapToDto(createdTask);
    }
}