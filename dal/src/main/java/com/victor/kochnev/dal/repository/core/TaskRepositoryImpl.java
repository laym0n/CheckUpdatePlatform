package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.dto.dal.GetTasksDalRequestDto;
import com.victor.kochnev.core.dto.dal.GetTasksDalResponseDto;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.TaskRepository;
import com.victor.kochnev.dal.converter.EntityTaskMapper;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.dal.entity.TaskEntity;
import com.victor.kochnev.dal.repository.jpa.PluginEntityRepository;
import com.victor.kochnev.dal.repository.jpa.TaskEntityRepository;
import com.victor.kochnev.dal.spec.TaskSpecification;
import com.victor.kochnev.domain.entity.Task;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

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

    @Override
    public Task getById(UUID id) {
        TaskEntity taskEntity = getTaskEntityByById(id);
        return taskMapper.mapToDomain(taskEntity);
    }

    @Override
    @Transactional
    public Task update(Task task) {
        TaskEntity dbTaskEntity = getTaskEntityByById(task.getId());
        TaskEntity updatedTask = taskMapper.mapToEntity(task);
        taskMapper.update(dbTaskEntity, updatedTask);
        return taskMapper.mapToDomain(dbTaskEntity);
    }

    @Override
    public GetTasksDalResponseDto getByFilters(GetTasksDalRequestDto dalRqDto) {
        var spec = prepareSpecification(dalRqDto);
        List<TaskEntity> taskEntities = taskRepository.findAll(spec);
        List<Task> tasks = taskEntities.stream()
                .map(taskMapper::mapToDomain)
                .toList();

        var responseDto = new GetTasksDalResponseDto();
        responseDto.setTasks(tasks);
        return responseDto;
    }

    private Specification<TaskEntity> prepareSpecification(GetTasksDalRequestDto dalRqDto) {
        Specification<TaskEntity> spec = Specification.where(TaskSpecification.getAll());
        if (dalRqDto == null || dalRqDto.getFilters() == null) {
            return spec;
        }
        var filters = dalRqDto.getFilters();
        if (ObjectUtils.isNotEmpty(filters.getIds())) {
            spec = spec.and(TaskSpecification.byIds(filters.getIds()));
        }
        if (ObjectUtils.isNotEmpty(filters.getOwnerIds())) {
            spec = spec.and(TaskSpecification.byOwnerIds(filters.getOwnerIds()));
        }
        if (ObjectUtils.isNotEmpty(filters.getPluginIds())) {
            spec = spec.and(TaskSpecification.byPluginIds(filters.getPluginIds()));
        }
        return spec;
    }

    private TaskEntity getTaskEntityByById(UUID id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create(Task.class, id.toString(), "id"));
    }
}
