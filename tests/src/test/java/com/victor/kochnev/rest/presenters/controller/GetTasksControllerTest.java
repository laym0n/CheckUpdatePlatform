package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.domain.entity.TaskDto;
import com.victor.kochnev.core.dto.request.GetTasksRequestDto;
import com.victor.kochnev.core.dto.request.TasksFilterDto;
import com.victor.kochnev.core.dto.response.GetTasksResponseDto;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.TaskEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.domain.enums.UserRole;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetTasksControllerTest extends BaseControllerTest {
    private static final String GET_PLUGIN_USAGES_ENDPOINT = "/tasks";
    private static UUID OWNER_ID;
    private static UUID USER_ID1;
    private static UUID PLUGIN_ID1;
    private static UUID TASK_ID1;
    private static UUID TASK_ID2;
    private static UUID TASK_ID3;
    private static UUID TASK_ID4;
    private static UUID TASK_ID5;
    private static UserEntity userForRequest;
    @Autowired
    PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Test
    void successGetAll() {
        //Assign
        prepareDb();
        var requestDto = prepareRequest();
        String uri = getUri(requestDto);

        //Action
        MvcResult mvcResult = get(uri, requestDto, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        var response = getResponseDto(mvcResult, GetTasksResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.getTasks());
        assertEquals(5, response.getTasks().size());
    }

    @SneakyThrows
    @Test
    void successGet_byIds() {
        //Assign
        prepareDb();
        var requestDto = prepareRequest();
        requestDto.getFilters().setIds(List.of(TASK_ID1));
        String uri = getUri(requestDto);

        //Action
        MvcResult mvcResult = get(uri, requestDto, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        var response = getResponseDto(mvcResult, GetTasksResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.getTasks());
        assertEquals(1, response.getTasks().size());
        assertContains(response.getTasks(), TASK_ID1);
    }

    private GetTasksRequestDto prepareRequest() {
        var requestDto = new GetTasksRequestDto();
        requestDto.setFilters(new TasksFilterDto());
        return requestDto;
    }

    private void assertContains(List<TaskDto> tasks, UUID id) {
        assertTrue(tasks.stream().anyMatch(usage -> usage.getId().equals(id)));
    }

    private String getUri(GetTasksRequestDto requestDto) {
        if (requestDto == null || requestDto.getFilters() == null) {
            return GET_PLUGIN_USAGES_ENDPOINT;
        }
        StringBuilder uri = new StringBuilder(GET_PLUGIN_USAGES_ENDPOINT);
        boolean isFirst = true;
        if (requestDto.getFilters().getIds() != null) {
            for (UUID id : requestDto.getFilters().getIds()) {
                uri.append(isFirst ? "?" : "$");
                uri.append("filters.ids=").append(id);
                isFirst = false;
            }
        }
        return uri.toString();
    }

    private void prepareDb() {
        OWNER_ID = userRepository.save(UserEntityBuilder.postfixBuilder(1).build()).getId();

        PLUGIN_ID1 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userRepository.findById(OWNER_ID).get())
                .build()).getId();

        TASK_ID1 = taskRepository.save(TaskEntityBuilder.persistedPostfixBuilder(1)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        TASK_ID2 = taskRepository.save(TaskEntityBuilder.persistedPostfixBuilder(2)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        TASK_ID3 = taskRepository.save(TaskEntityBuilder.persistedPostfixBuilder(3)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        TASK_ID4 = taskRepository.save(TaskEntityBuilder.persistedPostfixBuilder(4)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        TASK_ID5 = taskRepository.save(TaskEntityBuilder.persistedPostfixBuilder(5)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .build()).getId();

        USER_ID1 = userRepository.save(UserEntityBuilder.postfixBuilder(2)
                .roles(List.of(UserRole.EMPLOYEE)).build()).getId();
        userForRequest = userRepository.findById(USER_ID1).get();
    }
}
