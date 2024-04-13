package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.request.MakeDecisionRequestDto;
import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.domain.enums.PluginStatus;
import com.victor.kochnev.domain.enums.TaskDecision;
import com.victor.kochnev.domain.enums.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskControllerDecisionMakingTest extends BaseControllerTest {
    private static final String COMMENT = "comment";
    private static final String TASK_CREATE_ENDPOINT = "/task/%s/decision";
    private UUID USER_ID;
    private UUID PLUGIN_ID;
    private UUID TASK_ID;
    private UserEntity userForRequest;

    @Test
    void makeApproveDecision() {
        //Assign
        prepareDb();

        var requestBody = prepareRequest(TaskDecision.APPROVE);

        String url = String.format(TASK_CREATE_ENDPOINT, TASK_ID);

        //Action
        MvcResult mvcResult = put(url, requestBody, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        TaskEntity taskEntity = taskRepository.findById(TASK_ID).get();
        assertEquals(TaskDecision.APPROVE, taskEntity.getDecision());
        assertEquals(COMMENT, taskEntity.getComment());

        PluginEntity plugin = pluginRepository.findById(PLUGIN_ID).get();
        assertNotNull(plugin.getDescription());
        assertEquals(PluginStatus.ACTIVE, plugin.getStatus());
        assertEquals(taskEntity.getDescription(), plugin.getDescription());
    }

    @Test
    void makeRejectDecision() {
        //Assign
        prepareDb();

        var requestBody = prepareRequest(TaskDecision.REJECT);

        String url = String.format(TASK_CREATE_ENDPOINT, TASK_ID);

        //Action
        MvcResult mvcResult = put(url, requestBody, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        TaskEntity taskEntity = taskRepository.findById(TASK_ID).get();
        assertEquals(TaskDecision.REJECT, taskEntity.getDecision());
        assertEquals(COMMENT, taskEntity.getComment());

        PluginEntity plugin = pluginRepository.findById(PLUGIN_ID).get();
        assertNull(plugin.getDescription());
        assertEquals(PluginStatus.CREATED, plugin.getStatus());
    }

    @Test
    void makeApproveDecision_WhenPluginStatusIsACTIVE() {
        //Assign
        prepareDb();
        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        pluginEntity.setStatus(PluginStatus.ACTIVE);
        pluginRepository.save(pluginEntity);

        var requestBody = prepareRequest(TaskDecision.APPROVE);

        String url = String.format(TASK_CREATE_ENDPOINT, TASK_ID);

        //Action
        MvcResult mvcResult = put(url, requestBody, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        TaskEntity taskEntity = taskRepository.findById(TASK_ID).get();
        assertEquals(TaskDecision.APPROVE, taskEntity.getDecision());
        assertEquals(COMMENT, taskEntity.getComment());

        PluginEntity plugin = pluginRepository.findById(PLUGIN_ID).get();
        assertNotNull(plugin.getDescription());
        assertEquals(PluginStatus.ACTIVE, plugin.getStatus());
        assertEquals(taskEntity.getDescription(), plugin.getDescription());
    }

    @Test
    void makeRejectDecision_WhenPluginStatusIsACTIVE() {
        //Assign
        prepareDb();
        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        pluginEntity.setStatus(PluginStatus.ACTIVE);
        pluginRepository.save(pluginEntity);

        var requestBody = prepareRequest(TaskDecision.REJECT);

        String url = String.format(TASK_CREATE_ENDPOINT, TASK_ID);

        //Action
        MvcResult mvcResult = put(url, requestBody, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        TaskEntity taskEntity = taskRepository.findById(TASK_ID).get();
        assertEquals(TaskDecision.REJECT, taskEntity.getDecision());
        assertEquals(COMMENT, taskEntity.getComment());

        PluginEntity plugin = pluginRepository.findById(PLUGIN_ID).get();
        assertNull(plugin.getDescription());
        assertEquals(PluginStatus.ACTIVE, plugin.getStatus());
    }


    @Test
    void makeDecision_WhenSimpleUserSendRequest_Expect403() {
        //Assign
        prepareDb();
        UserEntity user = userRepository.findById(USER_ID).get();
        user.setRoles(List.of(UserRole.SIMPLE_USER));
        user = userRepository.save(user);

        var requestBody = prepareRequest(TaskDecision.APPROVE);

        String url = String.format(TASK_CREATE_ENDPOINT, TASK_ID);

        //Action
        MvcResult mvcResult = post(url, requestBody, prepareSimpleUserHeaders(user));

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.FORBIDDEN);

        TaskEntity taskEntity = taskRepository.findById(TASK_ID).get();
        assertNull(taskEntity.getDecision());
    }

    private MakeDecisionRequestDto prepareRequest(TaskDecision decision) {
        var requestBody = new MakeDecisionRequestDto();
        requestBody.setDecision(decision);
        requestBody.setComment(COMMENT);
        return requestBody;
    }

    private void prepareDb() {
        USER_ID = userRepository.save(UserEntityBuilder.defaultBuilder()
                .roles(List.of(UserRole.EMPLOYEE)).build()).getId();
        PLUGIN_ID = pluginRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
                .ownerUser(userRepository.findById(USER_ID).get())
                .status(PluginStatus.CREATED)
                .description(null)
                .build()).getId();
        TASK_ID = taskRepository.save(TaskEntityBuilder.defaultBuilder()
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .build()).getId();
        userForRequest = userRepository.findById(USER_ID).get();
    }
}
