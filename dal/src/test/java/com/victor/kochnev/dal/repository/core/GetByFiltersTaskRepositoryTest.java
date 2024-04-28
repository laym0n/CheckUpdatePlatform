package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.dto.dal.GetTasksDalRequestDto;
import com.victor.kochnev.core.dto.dal.TasksFilterDalDto;
import com.victor.kochnev.core.repository.TaskRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.TaskEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.domain.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetByFiltersTaskRepositoryTest extends BaseBootTest {
    private static UUID OWNER_ID;
    private static UUID PLUGIN_ID1;
    private static UUID TASK_ID1;
    private static UUID TASK_ID2;
    private static UUID TASK_ID3;
    private static UUID TASK_ID4;
    private static UUID TASK_ID5;
    @Autowired
    TaskRepository taskRepository;

    @Test
    void testGetByFilters_All() {
        //Assign
        prepareDb();
        var request = prepareRequest();

        //Action
        var response = taskRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(5, response.getTasks().size());
    }

    @Test
    void testGetByFilters_byIds() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setIds(List.of(TASK_ID1, TASK_ID2));

        //Action
        var response = taskRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(2, response.getTasks().size());

        assertContains(response.getTasks(), TASK_ID1);
        assertContains(response.getTasks(), TASK_ID2);
    }

    @Test
    void testGetByFilters_byOwnerIds() {
        //Assign
        prepareDb();
        UUID userId1 = userEntityRepository.save(UserEntityBuilder.persistedPostfixBuilder(2).build()).getId();
        UUID pluginId2 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(2)
                .ownerUser(userEntityRepository.findById(userId1).get()).build()).getId();
        UUID taskId6 = taskEntityRepository.save(TaskEntityBuilder.persistedPostfixBuilder(6)
                .plugin(pluginEntityRepository.findById(pluginId2).get()).build()).getId();

        var request = prepareRequest();
        request.getFilters().setOwnerIds(List.of(OWNER_ID));

        //Action
        var response = taskRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(5, response.getTasks().size());

        assertContains(response.getTasks(), TASK_ID1);
        assertContains(response.getTasks(), TASK_ID2);
        assertContains(response.getTasks(), TASK_ID3);
        assertContains(response.getTasks(), TASK_ID4);
        assertContains(response.getTasks(), TASK_ID5);
    }

    @Test
    void testGetByFilters_byPluginIds() {
        //Assign
        prepareDb();
        UUID pluginId2 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(2)
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .build()).getId();
        UUID taskId6 = taskEntityRepository.save(TaskEntityBuilder.persistedPostfixBuilder(6)
                .plugin(pluginEntityRepository.findById(pluginId2).get())
                .build()).getId();

        var request = prepareRequest();
        request.getFilters().setPluginIds(List.of(pluginId2));

        //Action
        var response = taskRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(1, response.getTasks().size());

        assertContains(response.getTasks(), taskId6);
    }


    private void assertContains(List<Task> entities, UUID id) {
        assertTrue(entities.stream().anyMatch(observing -> observing.getId().equals(id)));
    }

    private GetTasksDalRequestDto prepareRequest() {
        var request = new GetTasksDalRequestDto();
        request.setFilters(new TasksFilterDalDto());
        return request;
    }

    private void prepareDb() {
        OWNER_ID = userEntityRepository.save(UserEntityBuilder.postfixBuilder(1).build()).getId();

        PLUGIN_ID1 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .build()).getId();

        TASK_ID1 = taskEntityRepository.save(TaskEntityBuilder.persistedPostfixBuilder(1)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        TASK_ID2 = taskEntityRepository.save(TaskEntityBuilder.persistedPostfixBuilder(2)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        TASK_ID3 = taskEntityRepository.save(TaskEntityBuilder.persistedPostfixBuilder(3)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        TASK_ID4 = taskEntityRepository.save(TaskEntityBuilder.persistedPostfixBuilder(4)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        TASK_ID5 = taskEntityRepository.save(TaskEntityBuilder.persistedPostfixBuilder(5)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
    }
}
